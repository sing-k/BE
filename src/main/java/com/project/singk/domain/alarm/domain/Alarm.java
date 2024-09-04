package com.project.singk.domain.alarm.domain;

import com.project.singk.domain.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Alarm {
    private final Long id;
    private final AlarmType type;
    private final Member receiver;
    private final Member sender;
    private final Long targetId;
    private final boolean isRead;
    private final LocalDateTime createdAt;

    @Builder
    public Alarm(Long id, AlarmType type, Member receiver, Member sender, Long targetId, boolean isRead, LocalDateTime createdAt) {
        this.id = id;
        this.type = type;
        this.receiver = receiver;
        this.sender = sender;
        this.targetId = targetId;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }

    public static Alarm from(AlarmCreate alarmCreate, Member sender, Member receiver) {
        return Alarm.builder()
                .type(alarmCreate.getType())
                .sender(sender)
                .receiver(receiver)
                .targetId(alarmCreate.getTargetId())
                .isRead(false)
                .build();
    }
}
