package com.project.singk.domain.review.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AlbumReviewStatistics {
    private final long totalReviewer;
    private final long totalScore;
    private final double averageScore;
    private final long score1Count;
    private final long score2Count;
    private final long score3Count;
    private final long score4Count;
    private final long score5Count;
    private final long maleCount;
    private final long femaleCount;

    @Builder
    public AlbumReviewStatistics(long totalReviewer, long totalScore, double averageScore, long score1Count, long score2Count, long score3Count, long score4Count, long score5Count, long maleCount, long femaleCount) {
        this.totalReviewer = totalReviewer;
        this.totalScore = totalScore;
        this.averageScore = averageScore;
        this.score1Count = score1Count;
        this.score2Count = score2Count;
        this.score3Count = score3Count;
        this.score4Count = score4Count;
        this.score5Count = score5Count;
        this.maleCount = maleCount;
        this.femaleCount = femaleCount;
    }
}
