package com.project.singk.domain.alarm.controller.port;

import com.project.singk.domain.alarm.domain.AlarmType;
import com.project.singk.domain.member.domain.Member;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface AlarmService {
    SseEmitter subscribe(String username,String lastEventId);
    // Todo: 알람 보낼 서비스 메소드 안에 send()로 넣으시면 됩니다. -> AlarmType도 자체 수정
    void send(Member receiver, AlarmType alarmType, String content);
    Void delete(String id);
}
