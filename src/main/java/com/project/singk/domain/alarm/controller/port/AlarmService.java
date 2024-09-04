package com.project.singk.domain.alarm.controller.port;

import com.project.singk.domain.alarm.domain.AlarmCreate;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface AlarmService {
    SseEmitter subscribe(Long memberId ,String lastEventId);
    void send(AlarmCreate alarmCreate);
    void unsubscribe(Long id);
}
