package com.project.singk.domain.alarm.controller.port;

import com.project.singk.domain.alarm.domain.AlarmType;
import com.project.singk.domain.member.domain.Member;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface AlarmService {
    SseEmitter subscribe(Long memberId ,String lastEventId);
    void send(Member receiver, AlarmType alarmType, String content);
    Void unsubscribe(Long id);
}
