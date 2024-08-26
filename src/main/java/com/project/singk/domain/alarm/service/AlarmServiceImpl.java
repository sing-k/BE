package com.project.singk.domain.alarm.service;

import com.project.singk.domain.alarm.controller.port.AlarmService;
import com.project.singk.domain.alarm.controller.response.AlarmResponse;
import com.project.singk.domain.alarm.domain.Alarm;
import com.project.singk.domain.alarm.domain.AlarmType;
import com.project.singk.domain.alarm.service.port.AlarmRepository;
import com.project.singk.domain.alarm.service.port.EmitterRepository;
import com.project.singk.domain.common.service.port.ClockHolder;
import com.project.singk.domain.member.domain.Member;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;
import java.util.Map;

@Service
@Builder
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService {
    private static final Long SSE_TIMEOUT = 120L * 1000 * 60;

    private final EmitterRepository emitterRepository;
    private final AlarmRepository alarmRepository;
    private final ClockHolder clockHolder;

    @Override
    public SseEmitter subscribe(Long memberId, String lastEventId){
        String emitterId = makeTimeIncludeId(memberId);
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(SSE_TIMEOUT));

        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        String eventId = makeTimeIncludeId(memberId);
        sendAlarm(emitter, eventId, emitterId, "EventStream Created. [memberId=" + memberId + "]");


        if (hasLostData(lastEventId)) {
            sendLostData(lastEventId, memberId, emitterId, emitter);
        }

        return emitter;
    }
    @Override
    public void send(Member receiver, AlarmType alarmType, String content){
        Alarm alarm = alarmRepository.save(createAlarm(receiver, alarmType, content));

        String receiverId = String.valueOf(receiver.getId());
        String eventId = makeTimeIncludeId(receiver.getId());
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByMemberId(receiverId);
        emitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, alarm);
                    sendAlarm(emitter, eventId, key, AlarmResponse.from(alarm));
                }
        );
    }
    @Override
    public Void unsubscribe(Long id){
        alarmRepository.deleteById(id);
        return null;
    }
    private String makeTimeIncludeId(Long memberId) {
        return memberId + "_" + clockHolder.millis();
    }
    private void sendAlarm(SseEmitter emitter, String eventId, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .name("sse")
                    .data(data)
            );
        } catch (IOException exception) {
            emitterRepository.deleteById(emitterId);
        }
    }
    private boolean hasLostData(String lastEventId) {
        return !lastEventId.isEmpty();
    }

    private void sendLostData(String lastEventId, Long memberId, String emitterId, SseEmitter emitter) {
        Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByMemberId(String.valueOf(memberId));
        eventCaches.entrySet().stream()
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                .forEach(entry -> sendAlarm(emitter, entry.getKey(), emitterId, entry.getValue()));
    }
    private Alarm createAlarm(Member receiver, AlarmType alarmType, String content){
        return Alarm.builder()
                .receiver(receiver)
                .type(alarmType)
                .content(content)
                .isRead(false)
                .build();
    }

}
