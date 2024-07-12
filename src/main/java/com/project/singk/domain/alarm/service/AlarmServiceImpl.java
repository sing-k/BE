package com.project.singk.domain.alarm.service;

import com.project.singk.domain.alarm.controller.port.AlarmService;
import com.project.singk.domain.alarm.controller.response.AlarmResponse;
import com.project.singk.domain.alarm.domain.Alarm;
import com.project.singk.domain.alarm.domain.AlarmType;
import com.project.singk.domain.alarm.service.port.AlarmRepository;
import com.project.singk.domain.alarm.service.port.EmitterRepository;
import com.project.singk.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService {
    private static final Long SSE_TIMEOUT = 120L * 1000 * 60;

    private final EmitterRepository emitterRepository;
    private final AlarmRepository alarmRepository;

    @Override
    public SseEmitter subscribe(String username,String lastEventId){
        String emitterId = makeTimeIncludeId(username);
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(SSE_TIMEOUT));

        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        String eventId = makeTimeIncludeId(username);
        sendAlarm(emitter, eventId, emitterId, "EventStream Created. [userEmail=" + username + "]");


        if (hasLostData(lastEventId)) {
            sendLostData(lastEventId, username, emitterId, emitter);
        }

        return emitter;
    }
    @Override
    public void send(Member receiver, AlarmType alarmType, String content){
        Alarm alarm = alarmRepository.save(createAlarm(receiver, alarmType, content));

        String receiverEmail = receiver.getEmail();
        String eventId = makeTimeIncludeId(receiverEmail);
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByMemberId(receiverEmail);
        emitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, alarm);
                    sendAlarm(emitter, eventId, key, AlarmResponse.from(alarm));
                }
        );
    }
    @Override
    public Void delete(String id){
        alarmRepository.deleteById(id);
        return null;
    }
    private String makeTimeIncludeId(String email) {
        return email + "_" + System.currentTimeMillis();
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

    private void sendLostData(String lastEventId, String userEmail, String emitterId, SseEmitter emitter) {
        Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByMemberId(String.valueOf(userEmail));
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
