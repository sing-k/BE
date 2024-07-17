package com.project.singk.domain.activity.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.singk.domain.activity.domain.ActivityScore;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Builder
@ToString
public class ActivityGraphResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM.dd", timezone = "Asia/Seoul")
    private LocalDate date;
    private int score;

    public static ActivityGraphResponse from (LocalDate date, int score) {
        return ActivityGraphResponse.builder()
                .date(date)
                .score(score)
                .build();
    }
    public static ActivityGraphResponse from (ActivityScore activityScore) {
        return ActivityGraphResponse.builder()
                .date(activityScore.getDate())
                .score(activityScore.getScore())
                .build();
    }
}
