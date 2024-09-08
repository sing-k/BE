package com.project.singk.domain.alarm.controller;

import com.project.singk.domain.alarm.controller.port.AlarmService;
import com.project.singk.domain.alarm.controller.response.AlarmResponse;
import com.project.singk.domain.member.controller.port.AuthService;
import com.project.singk.domain.member.controller.port.MemberService;
import com.project.singk.domain.post.controller.request.FilterSort;
import com.project.singk.domain.post.controller.request.PostSort;
import com.project.singk.domain.post.controller.response.FreePostResponse;
import com.project.singk.global.api.BaseResponse;
import com.project.singk.global.api.OffsetPageResponse;
import com.project.singk.global.validate.ValidEnum;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alarms")
public class AlarmController {

    private final AlarmService alarmService;
    private final AuthService authService;

    @GetMapping(value="/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(
            @RequestParam(value="Last-Event-ID", required = false, defaultValue = "") String lastEventId
    ) {
        return alarmService.subscribe(
                authService.getLoginMemberId(),
                lastEventId
        );
    }

    @DeleteMapping(value="/{alarmId}")
    public BaseResponse<Void> unsubscribe(
            @PathVariable(value = "alarmId") Long alarmId
    ) {
        alarmService.unsubscribe(alarmId);
        return BaseResponse.ok();
    }

    @GetMapping("")
    public BaseResponse<OffsetPageResponse<AlarmResponse>> getMyAlarms(
            @Range(min = 0, max = 1000, message = "offset은 0에서 1000사이의 값 이어야 합니다.") @RequestParam("offset") int offset,
            @Range(min = 0, max = 50, message = "limit은 0에서 50사이의 값 이어야 합니다.") @RequestParam("limit") int limit
    ) {

        return BaseResponse.ok(
                alarmService.getMyAlarms(
                        authService.getLoginMemberId(),
                        offset,
                        limit
                )
        );
    }
}
