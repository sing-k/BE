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
    private long count;
    private long totalScore;
    private double averageScore;
    private List<ScoreStatistics> scoreStatistics;
    private List<GenderStatistics> genderStatistics;

    public static AlbumReviewStatisticsResponse from(AlbumReviewStatistics statistics) {
        return AlbumReviewStatisticsResponse.builder()
                .count(statistics.getTotalReviewer())
                .totalScore(statistics.getTotalScore())
                .averageScore(statistics.calculateAverage(statistics.getTotalScore(), statistics.getTotalReviewer()))
                .scoreStatistics(List.of(
                        ScoreStatistics.builder()
                                .score(1)
                                .count(statistics.getScore1Count())
                                .ratio(statistics.calculateRatio(statistics.getScore1Count(), statistics.getTotalReviewer()))
                                .build(),
                        ScoreStatistics.builder()
                                .score(2)
                                .count(statistics.getScore2Count())
                                .ratio(statistics.calculateRatio(statistics.getScore2Count(), statistics.getTotalReviewer()))
                                .build(),
                        ScoreStatistics.builder()
                                .score(3)
                                .count(statistics.getScore3Count())
                                .ratio(statistics.calculateRatio(statistics.getScore3Count(), statistics.getTotalReviewer()))
                                .build(),
                        ScoreStatistics.builder()
                                .score(4)
                                .count(statistics.getScore4Count())
                                .ratio(statistics.calculateRatio(statistics.getScore4Count(), statistics.getTotalReviewer()))
                                .build(),
                        ScoreStatistics.builder()
                                .score(5)
                                .count(statistics.getScore5Count())
                                .ratio(statistics.calculateRatio(statistics.getScore5Count(), statistics.getTotalReviewer()))
                                .build()
                ))
                .genderStatistics(List.of(
                        GenderStatistics.builder()
                                .gender(Gender.MALE.getName())
                                .count(statistics.getMaleCount())
                                .totalScore(statistics.getMaleTotalScore())
                                .ratio(statistics.calculateRatio(statistics.getMaleCount(), statistics.getTotalReviewer()))
                                .averageScore(statistics.calculateAverage(statistics.getMaleTotalScore(), statistics.getMaleCount()))
                                .build(),
                        GenderStatistics.builder()
                                .gender(Gender.FEMALE.getName())
                                .count(statistics.getFemaleCount())
                                .totalScore(statistics.getFemaleTotalScore())
                                .ratio(statistics.calculateRatio(statistics.getFemaleCount(), statistics.getTotalReviewer()))
                                .averageScore(statistics.calculateAverage(statistics.getFemaleTotalScore(), statistics.getFemaleCount()))
                                .build()
                ))
                .build();
    }

    @Getter
    @Builder
    @ToString
    public static class ScoreStatistics {
        private int score;
        private long count;
        private double ratio;
    }

    @Getter
    @Builder
    @ToString
    public static class GenderStatistics {
        private String gender;
        private long count;
        private long totalScore;
        private double ratio;
        private double averageScore;
    }
}
