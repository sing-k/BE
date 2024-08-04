package com.project.singk.domain.review.controller.response;

import com.project.singk.domain.review.domain.AlbumReviewStatistics;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class AlbumReviewStatisticsSimpleResponse {
    private Long id;
    private long count;
    private double averageScore;

    public static AlbumReviewStatisticsSimpleResponse from(AlbumReviewStatistics statistics) {
        return AlbumReviewStatisticsSimpleResponse.builder()
                .id(statistics.getId())
                .count(statistics.getTotalReviewer())
                .averageScore(statistics.calculateAverage(statistics.getTotalScore(), statistics.getTotalReviewer()))
                .build();
    }
}
