package com.project.singk.domain.alarm.controller;

import com.project.singk.domain.alarm.controller.port.AlarmService;
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

    @GetMapping(value="/subscribe",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public BaseResponse<SseEmitter> subscribe(@RequestHeader(value="Last-Event-ID",required = false, defaultValue = "") String lastEventId){
        // Todo: username-> (email) 받아오는 방법 고려
        String username="email@email.com";
        return BaseResponse.ok(alarmService.subscribe(username,lastEventId));
    }

    @DeleteMapping(value="/{alarmId}")
    public BaseResponse<Void> delete(@PathVariable(value = "alarmId") String id){
        alarmService.delete(id);
        return BaseResponse.ok();
    }
}
