package com.project.singk.domain.review.domain;

import com.project.singk.domain.member.domain.Gender;
import com.project.singk.domain.member.domain.Member;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AlbumReviewStatistics {
    private final Long id;
    private final int totalReviewer;
    private final int totalScore;
    private final double averageScore;
    private final int score1Count;
    private final int score2Count;
    private final int score3Count;
    private final int score4Count;
    private final int score5Count;
    private final int maleCount;
    private final int maleTotalScore;
    private final int femaleCount;
    private final int femaleTotalScore;
    private final LocalDateTime modifiedAt;

    @Builder
    @QueryProjection
    public AlbumReviewStatistics(Long id, int totalReviewer, int totalScore, double averageScore, int score1Count, int score2Count, int score3Count, int score4Count, int score5Count, int maleCount, int maleTotalScore, int femaleCount, int femaleTotalScore, LocalDateTime modifiedAt) {
        this.id = id;
        this.totalReviewer = totalReviewer;
        this.totalScore = totalScore;
        this.averageScore = averageScore;
        this.score1Count = score1Count;
        this.score2Count = score2Count;
        this.score3Count = score3Count;
        this.score4Count = score4Count;
        this.score5Count = score5Count;
        this.maleCount = maleCount;
        this.maleTotalScore = maleTotalScore;
        this.femaleCount = femaleCount;
        this.femaleTotalScore = femaleTotalScore;
        this.modifiedAt = modifiedAt;
    }

    public double calculateAverage(long a, long b) {
        if (b == 0) return 0.0;

        return Math.round((double) a / b * 100) / 100.0;
    }

    public double calculateRatio(long target, long total) {
        if (total == 0) return 0.0;

        return Math.round(((double) target / total) * 100);
    }

    public static AlbumReviewStatistics empty() {
        return AlbumReviewStatistics.builder()
                .totalReviewer(0)
                .totalScore(0)
                .averageScore(0)
                .score1Count(0)
                .score2Count(0)
                .score3Count(0)
                .score4Count(0)
                .score5Count(0)
                .maleCount(0)
                .maleTotalScore(0)
                .femaleCount(0)
                .femaleTotalScore(0)
                .build();
    }
    public AlbumReviewStatistics update(Member reviewer, AlbumReview review, boolean isDelete) {
        if (isDelete) {
            return deleteReview(reviewer, review);
        }

        return createReview(reviewer, review);
    }

    private AlbumReviewStatistics createReview(Member reviewer, AlbumReview review) {
        return AlbumReviewStatistics.builder()
                .id(this.id)
                .totalReviewer(this.totalReviewer + 1)
                .totalScore(this.totalScore + review.getScore())
                .averageScore(this.calculateAverage(
                        this.totalScore + review.getScore(),
                        this.totalReviewer + 1
                ))
                .score1Count(review.getScore() == 1 ? this.score1Count + 1 : this.score1Count)
                .score2Count(review.getScore() == 2 ? this.score2Count + 1 : this.score2Count)
                .score3Count(review.getScore() == 3 ? this.score3Count + 1 : this.score3Count)
                .score4Count(review.getScore() == 4 ? this.score4Count + 1 : this.score4Count)
                .score5Count(review.getScore() == 5 ? this.score5Count + 1 : this.score5Count)
                .maleCount(reviewer.getGender() == Gender.MALE ? this.maleCount + 1 : this.maleCount)
                .maleTotalScore(reviewer.getGender() == Gender.MALE ? this.maleTotalScore + review.getScore() : this.maleTotalScore)
                .femaleCount(reviewer.getGender() == Gender.FEMALE ? this.femaleCount + 1 : this.femaleCount)
                .femaleTotalScore(reviewer.getGender() == Gender.FEMALE ? this.femaleTotalScore + review.getScore() : this.femaleTotalScore)
                .build();
    }

    private AlbumReviewStatistics deleteReview(Member reviewer, AlbumReview review) {
        return AlbumReviewStatistics.builder()
                .id(this.id)
                .totalReviewer(this.totalReviewer - 1)
                .totalScore(this.totalScore - review.getScore())
                .averageScore(this.calculateAverage(
                        this.totalScore - review.getScore(),
                        this.totalReviewer - 1
                ))
                .score1Count(review.getScore() == 1 ? this.score1Count - 1 : this.score1Count)
                .score2Count(review.getScore() == 2 ? this.score2Count - 1 : this.score2Count)
                .score3Count(review.getScore() == 3 ? this.score3Count - 1 : this.score3Count)
                .score4Count(review.getScore() == 4 ? this.score4Count - 1 : this.score4Count)
                .score5Count(review.getScore() == 5 ? this.score5Count - 1 : this.score5Count)
                .maleCount(reviewer.getGender() == Gender.MALE ? this.maleCount - 1 : this.maleCount)
                .maleTotalScore(reviewer.getGender() == Gender.MALE ? this.maleTotalScore - review.getScore() : this.maleTotalScore)
                .femaleCount(reviewer.getGender() == Gender.FEMALE ? this.femaleCount - 1 : this.femaleCount)
                .femaleTotalScore(reviewer.getGender() == Gender.FEMALE ? this.femaleTotalScore - review.getScore() : this.femaleTotalScore)
                .build();
    }
}
