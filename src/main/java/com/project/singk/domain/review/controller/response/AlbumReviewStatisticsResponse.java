package com.project.singk.domain.review.controller.response;

import com.project.singk.domain.member.domain.Gender;
import com.project.singk.domain.review.domain.AlbumReviewStatistics;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class AlbumReviewStatisticsResponse {
    private long totalReviewer;
    private double averageScore;
    private List<ScoreStatistics> scoreStatistics;
    private List<GenderStatistics> genderStatistics;

    public static AlbumReviewStatisticsResponse from(AlbumReviewStatistics albumReviewStatistics) {
        return AlbumReviewStatisticsResponse.builder()
                .totalReviewer(albumReviewStatistics.getTotalReviewer())
                .averageScore(albumReviewStatistics.getAverageScore())
                .scoreStatistics(List.of(
                        ScoreStatistics.builder().score(1).count(albumReviewStatistics.getScore1Count()).build(),
                        ScoreStatistics.builder().score(2).count(albumReviewStatistics.getScore2Count()).build(),
                        ScoreStatistics.builder().score(3).count(albumReviewStatistics.getScore3Count()).build(),
                        ScoreStatistics.builder().score(4).count(albumReviewStatistics.getScore4Count()).build(),
                        ScoreStatistics.builder().score(5).count(albumReviewStatistics.getScore5Count()).build()
                ))
                .genderStatistics(List.of(
                        GenderStatistics.builder().gender(Gender.MALE.getName()).count(albumReviewStatistics.getMaleCount()).build(),
                        GenderStatistics.builder().gender(Gender.FEMALE.getName()).count(albumReviewStatistics.getFemaleCount()).build()
                ))
                .build();
    }

    @Getter
    @Builder
    @ToString
    public static class ScoreStatistics {
        private int score;
        private long count;
    }

    @Getter
    @Builder
    @ToString
    public static class GenderStatistics {
        private String gender;
        private long count;
    }
}
