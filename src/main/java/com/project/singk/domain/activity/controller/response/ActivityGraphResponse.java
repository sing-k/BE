package com.project.singk.domain.activity.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class ActivityGraphResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime date;
    private int score;

    public static ActivityGraphResponse from (LocalDateTime date, int score) {
        return ActivityGraphResponse.builder()
                .date(date)
                .score(score)
                .build();
    }
}
