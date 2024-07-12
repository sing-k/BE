package com.project.singk.domain.alarm.domain;

import com.project.singk.domain.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Alarm {

    private final String id;
    private final String content;
    private final Boolean isRead;
    private final Member receiver;
    private final AlarmType type;

    @Builder
    public Alarm(String id, String content, Boolean isRead, AlarmType type, Member receiver){
        this.id = id;
        this.content = content;
        this.isRead = isRead;
        this.type = type;
        this.receiver = receiver;
    }

}
