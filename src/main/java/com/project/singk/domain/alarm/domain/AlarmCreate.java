package com.project.singk.domain.alarm.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AlarmCreate {

    private final AlarmType type;
    private final Long senderId;
    private final Long receiverId;
    private final Long targetId;

    @Builder
    public AlarmCreate(AlarmType type, Long senderId, Long receiverId, Long targetId) {
        this.type = type;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.targetId = targetId;
    }

    public static AlarmCreate from (AlarmType type, Long senderId, Long receiverId, Long targetId) {
        return AlarmCreate.builder()
                .type(type)
                .senderId(senderId)
                .receiverId(receiverId)
                .targetId(targetId)
                .build();
    }
}
