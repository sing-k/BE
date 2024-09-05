package com.project.singk.domain.alarm.service;

import com.project.singk.domain.alarm.controller.port.AlarmService;
import com.project.singk.domain.alarm.controller.response.AlarmResponse;
import com.project.singk.domain.alarm.domain.Alarm;
import com.project.singk.domain.alarm.domain.AlarmCreate;
import com.project.singk.domain.alarm.service.port.AlarmRepository;
import com.project.singk.domain.alarm.service.port.EmitterRepository;
import com.project.singk.domain.alarm.service.port.EventCacheRepository; // 이벤트 캐시 저장소 추가
import com.project.singk.domain.common.service.port.ClockHolder;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.service.port.MemberRepository;
import com.project.singk.global.api.OffsetPageResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@Service
@Builder
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AlarmServiceImpl implements AlarmService {
    private static final Long SSE_TIMEOUT = 120L * 1000 * 60;

    private final EmitterRepository emitterRepository;
    private final AlarmRepository alarmRepository;
    private final MemberRepository memberRepository;
    private final ClockHolder clockHolder;
    private final EventCacheRepository eventCacheRepository; // 이벤트 캐시 저장소 추가

    @Override
    public SseEmitter subscribe(Long memberId, String lastEventId) {
        // 사용자에 대한 SseEmitter 세션 생성
        // SseEmitter는 클라이언트에게 이벤트를 전송하는 역할 수행
        String emitterId = makeTimeIncludeId(memberId);
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(SSE_TIMEOUT));

        // SseEmitter가 완료되면 삭제
        emitter.onCompletion(() -> {
            emitterRepository.deleteById(emitterId);
            eventCacheRepository.deleteEventsByMemberId(String.valueOf(memberId)); // 이벤트 삭제
        });

        // SseEmitter가 종료되면 삭제
        emitter.onTimeout(() -> {
            emitterRepository.deleteById(emitterId);
            eventCacheRepository.deleteEventsByMemberId(String.valueOf(memberId)); // 이벤트 삭제
        });

        // 503 에러를 방지하기 위한 더미 이벤트 전송
        String eventId = makeTimeIncludeId(memberId);
        sendAlarm(emitter, eventId, emitterId, "EventStream Created. [memberId=" + memberId + "]");

        // 재연결 시 저장소에 있는 이벤트를 전송
        if (hasLostData(lastEventId)) {
            sendLostData(lastEventId, memberId, emitterId, emitter);
        }

        log.info(String.format("[memberId:%d has subscribe emitterId:%s]", memberId, emitterId));

        return emitter;
    }

    @Override
    public void send(AlarmCreate alarmCreate) {
        Member sender = memberRepository.getById(alarmCreate.getSenderId());
        Member receiver = memberRepository.getById(alarmCreate.getReceiverId());

        Alarm alarm = alarmRepository.save(Alarm.from(alarmCreate, sender, receiver));

        String receiverId = String.valueOf(receiver.getId());
        String eventId = makeTimeIncludeId(receiver.getId());
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByMemberId(receiverId);

        // emitters가 비어 있는 경우 이벤트를 저장
        if (emitters.isEmpty()) {
            log.info("emitter is empty");
            eventCacheRepository.saveEvent(receiverId, eventId, AlarmResponse.from(alarm));
            return;
        }

        emitters.forEach((key, emitter) -> {
            // 전송 시도, 전송할 수 있는 emitter만 전송
            sendAlarm(emitter, eventId, key, AlarmResponse.from(alarm, alarmRepository.countIsReadFalseByMemberId(receiver.getId())));
            log.info(String.format("[alarm type:%s senderId:%d send to receiverId:%d emitterId:%s]", alarm.getType().toString(), sender.getId(), receiver.getId(), key));
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
        log.info(String.format("[memberId:%d has unsubscribe]"));
    }

    @Override
    public OffsetPageResponse<AlarmResponse> getMyAlarms(Long memberId, int offset, int limit) {
        Page<Alarm> alarms = alarmRepository.findAllByMemberId(memberId, offset, limit);

        return OffsetPageResponse.of(
                offset,
                limit,
                (int) alarms.getTotalElements(),
                alarms.stream()
                        .map(Alarm::read)
                        .map(alarmRepository::save)
                        .map(AlarmResponse::from)
                        .toList()
        );
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

}
