package com.project.singk.domain.alarm.controller.port;

import com.project.singk.domain.alarm.controller.response.AlarmResponse;
import com.project.singk.domain.alarm.domain.AlarmCreate;
import com.project.singk.global.api.OffsetPageResponse;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface AlarmService {
    SseEmitter subscribe(Long memberId ,String lastEventId);
    void send(AlarmCreate alarmCreate);
    void unsubscribe(Long id);
    OffsetPageResponse<AlarmResponse> getMyAlarms(Long memberId, int offset, int limit);
}
