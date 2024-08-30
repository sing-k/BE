package com.project.singk.domain.alarm.service;

import com.project.singk.domain.alarm.controller.port.AlarmService;
import com.project.singk.domain.alarm.controller.response.AlarmResponse;
import com.project.singk.domain.alarm.domain.Alarm;
import com.project.singk.domain.alarm.domain.AlarmType;
import com.project.singk.domain.alarm.service.port.AlarmRepository;
import com.project.singk.domain.alarm.service.port.EmitterRepository;
import com.project.singk.domain.alarm.service.port.EventCacheRepository; // 이벤트 캐시 저장소 추가
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
    private final EventCacheRepository eventCacheRepository; // 이벤트 캐시 저장소 추가

    @Override
    public SseEmitter subscribe(Long memberId, String lastEventId) {
        String emitterId = makeTimeIncludeId(memberId);
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(SSE_TIMEOUT));

        emitter.onCompletion(() -> {
            emitterRepository.deleteById(emitterId);
            eventCacheRepository.deleteEventsByMemberId(String.valueOf(memberId)); // 이벤트 삭제
        });

        emitter.onTimeout(() -> {
            emitterRepository.deleteById(emitterId);
            eventCacheRepository.deleteEventsByMemberId(String.valueOf(memberId)); // 이벤트 삭제
        });

        String eventId = makeTimeIncludeId(memberId);
        sendAlarm(emitter, eventId, emitterId, "EventStream Created. [memberId=" + memberId + "]");

        // 재연결 시 저장소에 있는 이벤트를 전송
        if (hasLostData(lastEventId)) {
            sendLostData(lastEventId, memberId, emitterId, emitter);
        }

        return emitter;
    }

    @Override
    public void send(Member receiver, AlarmType alarmType, String content) {
        Alarm alarm = alarmRepository.save(createAlarm(receiver, alarmType, content));

        String receiverId = String.valueOf(receiver.getId());
        String eventId = makeTimeIncludeId(receiver.getId());
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByMemberId(receiverId);

        // emitters가 비어 있는 경우 이벤트를 저장
        if (emitters.isEmpty()) {
            eventCacheRepository.saveEvent(receiverId, eventId, AlarmResponse.from(alarm));
            return;
        }

        emitters.forEach((key, emitter) -> {
            // 전송 시도, 전송할 수 있는 emitter만 전송
            sendAlarm(emitter, eventId, key, AlarmResponse.from(alarm));
        });
    }

    @Override
    public void unsubscribe(Long memberId) {
        // 모든 emitter를 찾아서 삭제
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByMemberId(String.valueOf(memberId));
        emitters.forEach((key, emitter) -> {
            emitterRepository.deleteById(key);
        });
        eventCacheRepository.deleteEventsByMemberId(String.valueOf(memberId));
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
        return lastEventId != null && !lastEventId.isEmpty();
    }

    private void sendLostData(String lastEventId, Long memberId, String emitterId, SseEmitter emitter) {
        // 저장소에 있는 이벤트만 가져와 전송
        Map<String, Object> lostEvents = eventCacheRepository.findAllEventsAfter(String.valueOf(memberId), lastEventId);

        lostEvents.forEach((eventKey, eventValue) -> {
            sendAlarm(emitter, eventKey, emitterId, eventValue);
            // 전송된 이벤트는 삭제
            eventCacheRepository.deleteEventsByMemberId(String.valueOf(memberId));
        });
    }

    private Alarm createAlarm(Member receiver, AlarmType alarmType, String content) {
        return Alarm.builder()
                .receiver(receiver)
                .type(alarmType)
                .content(content)
                .isRead(false)
                .build();
    }
}
