package com.project.singk.domain.activity.domain;

import com.project.singk.domain.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ActivityHistory {
    private final Long id;
    private final ActivityType type;
    private final int score;
    private final Member member;

    @Builder
    public ActivityHistory(Long id, ActivityType type, int score, Member member) {
        this.id = id;
        this.type = type;
        this.score = score;
        this.member = member;
    }

    public static ActivityHistory from(ActivityType type, Member member) {
        return ActivityHistory.builder()
                .type(type)
                .score(type.getScore())
                .member(member)
                .build();
    }
}
