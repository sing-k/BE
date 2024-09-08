package com.project.singk.domain.activity.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ActivityScore {
    private final int score;
    private final LocalDate date;

    @Builder
    public ActivityScore(int score, LocalDate date) {
        this.score = score;
        this.date = date;
    }

    public static ActivityScore from(int score, LocalDate date) {
        return ActivityScore.builder()
                .score(score)
                .date(date)
                .build();
    }
}
