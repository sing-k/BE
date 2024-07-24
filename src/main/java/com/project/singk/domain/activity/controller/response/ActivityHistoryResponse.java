package com.project.singk.domain.activity.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.singk.domain.activity.domain.ActivityHistory;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class ActivityHistoryResponse {
    private String content;
    private int score;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime date;

    public static ActivityHistoryResponse from (ActivityHistory activityHistory) {
        return ActivityHistoryResponse.builder()
                .content(activityHistory.getType().getContent())
                .score(activityHistory.getType().getScore())
                .date(activityHistory.getCreatedAt())
                .build();
    }
}
