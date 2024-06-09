package com.project.singk.domain.review.domain;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AlbumReviewStatistics {
    private final long totalReviewer;
    private final int totalScore;
    private final int score1Count;
    private final int score2Count;
    private final int score3Count;
    private final int score4Count;
    private final int score5Count;
    private final int maleCount;
    private final int maleTotalScore;
    private final int femaleCount;
    private final int femaleTotalScore;

    @Builder
    @QueryProjection
    public AlbumReviewStatistics(long totalReviewer, int totalScore, int score1Count, int score2Count, int score3Count, int score4Count, int score5Count, int maleCount, int maleTotalScore, int femaleCount, int femaleTotalScore) {
        this.totalReviewer = totalReviewer;
        this.totalScore = totalScore;
        this.score1Count = score1Count;
        this.score2Count = score2Count;
        this.score3Count = score3Count;
        this.score4Count = score4Count;
        this.score5Count = score5Count;
        this.maleCount = maleCount;
        this.maleTotalScore = maleTotalScore;
        this.femaleCount = femaleCount;
        this.femaleTotalScore = femaleTotalScore;
    }

    public double calculateAverage(long a, long b) {
        return Math.round((double) a / b);
    }

    public double calculateRatio(long target, long total) {
        return Math.round(((double) target / total) * 100);
    }
}
