package com.project.singk.domain.alarm.controller;

import com.project.singk.domain.alarm.controller.port.AlarmService;
import com.project.singk.domain.member.controller.port.AuthService;
import com.project.singk.domain.member.controller.port.MemberService;
import com.project.singk.global.api.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alarms")
public class AlarmController {

    private final AlarmService alarmService;
    private final AuthService authService;

    @GetMapping(value="/subscribe",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public BaseResponse<SseEmitter> subscribe(
            @RequestHeader(value="Last-Event-ID", required = false, defaultValue = "") String lastEventId
    ) {

        return BaseResponse.ok(alarmService.subscribe(
                authService.getLoginMemberId(),
                lastEventId
        ));
    }

    @DeleteMapping(value="/{alarmId}")
    public BaseResponse<Void> unsubscribe(
            @PathVariable(value = "alarmId") Long alarmId
    ) {
        alarmService.unsubscribe(alarmId);
        return BaseResponse.ok();
    }
}
