package com.project.singk.domain.activity.domain;

import com.project.singk.domain.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ActivityHistory {
    private final Long id;
    private final ActivityType type;
    private final int score;
    private final Member member;
    private final LocalDateTime createdAt;

    @Builder
    public ActivityHistory(Long id, ActivityType type, int score, Member member, LocalDateTime createdAt) {
        this.id = id;
        this.type = type;
        this.score = score;
        this.member = member;
        this.createdAt = createdAt;
    }

    public static ActivityHistory from(ActivityType type, Member member) {
        return ActivityHistory.builder()
                .type(type)
                .score(type.getScore())
                .member(member)
                .build();
    }

    public ActivityHistory setAccumulatedScore(int score) {
        return ActivityHistory.builder()
                .type(this.type)
                .score(score)
                .member(this.member)
                .createdAt(this.createdAt)
                .build();
    }
}
