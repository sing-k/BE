package com.project.singk.domain.alarm.controller.response;

import com.project.singk.domain.alarm.domain.Alarm;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AlarmResponse {
    String id;
    String content;
    String type;
    String receiverName;
    public static AlarmResponse from(Alarm alarm){
        return AlarmResponse.builder()
                .id(alarm.getId().toString())
                .content(alarm.getContent())
                .type(alarm.getType().toString())
                .receiverName(alarm.getReceiver().getName())
                .build();

    }
}
