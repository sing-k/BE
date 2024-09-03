package com.project.singk.domain.member.controller.response;

import com.project.singk.domain.member.domain.MemberStatistics;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class MemberStatisticsResponse {
    private int totalActivityScore;
    private int totalReview;
    private int totalReviewScore;
    private int totalFreePost;
    private int totalFreeComment;
    private int totalRecommendPost;
    private int totalRecommendComment;
    private double averageReviewScore;

    public static MemberStatisticsResponse from (MemberStatistics statistics) {
        return MemberStatisticsResponse.builder()
                .totalActivityScore(statistics.getTotalActivityScore())
                .totalReview(statistics.getTotalReview())
                .totalReviewScore(statistics.getTotalReviewScore())
                .totalFreePost(statistics.getTotalFreePost())
                .totalFreeComment(statistics.getTotalFreeComment())
                .totalRecommendPost(statistics.getTotalRecommendPost())
                .totalRecommendComment(statistics.getTotalRecommendComment())
                .averageReviewScore(statistics.calculateAverage(statistics.getTotalReviewScore(), statistics.getTotalReview()))
                .build();
    }
}
