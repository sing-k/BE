package com.project.singk.domain.alarm.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.singk.domain.alarm.domain.Alarm;
import com.project.singk.domain.member.controller.response.MemberSimpleResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AlarmResponse {
    private String id;
    private String content;
    private String url;
    private boolean isRead;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private long count;

    public static AlarmResponse from(Alarm alarm){
        return AlarmResponse.builder()
                .id(alarm.getId().toString())
                .content(alarm.getType().getContent().apply(alarm.getSender().getNickname()))
                .url(alarm.getType().getUrl().apply(alarm.getTargetId()))
                .isRead(alarm.isRead())
                .createdAt(alarm.getCreatedAt())
                .build();

    }
    public static AlarmResponse from(Alarm alarm, long count){
        return AlarmResponse.builder()
                .id(alarm.getId().toString())
                .content(alarm.getType().getContent().apply(alarm.getSender().getNickname()))
                .url(alarm.getType().getUrl().apply(alarm.getTargetId()))
                .isRead(alarm.isRead())
                .createdAt(alarm.getCreatedAt())
                .count(count)
                .build();

    }
}
