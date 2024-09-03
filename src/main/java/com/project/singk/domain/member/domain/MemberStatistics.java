package com.project.singk.domain.member.domain;

import com.project.singk.domain.activity.domain.ActivityHistory;
import com.project.singk.domain.review.domain.AlbumReview;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberStatistics {
    private Long id;
    private final int totalActivityScore;
    private final int totalReview;
    private final int totalReviewScore;
    private final int totalFreePost;
    private final int totalFreeComment;
    private final int totalRecommendPost;
    private final int totalRecommendComment;

    @Builder
    public MemberStatistics(Long id, int totalActivityScore, int totalReview, int totalReviewScore, int totalFreePost, int totalFreeComment, int totalRecommendPost, int totalRecommendComment) {
        this.id = id;
        this.totalActivityScore = totalActivityScore;
        this.totalReview = totalReview;
        this.totalReviewScore = totalReviewScore;
        this.totalFreePost = totalFreePost;
        this.totalFreeComment = totalFreeComment;
        this.totalRecommendPost = totalRecommendPost;
        this.totalRecommendComment = totalRecommendComment;
    }

    public double calculateAverage(long a, long b) {
        if (b == 0) return 0.0;

        return Math.round((double) a / b * 100) / 100.0;
    }

    public static MemberStatistics empty() {
        return MemberStatistics.builder()
                .totalActivityScore(0)
                .totalReview(0)
                .totalReviewScore(0)
                .totalFreePost(0)
                .totalFreeComment(0)
                .totalRecommendPost(0)
                .totalRecommendComment(0)
                .build();
    }

    public MemberStatistics updateActivity(ActivityHistory activityHistory) {
        return MemberStatistics.builder()
                .id(this.id)
                .totalActivityScore(this.totalActivityScore + activityHistory.getScore())
                .totalReview(this.totalReview)
                .totalReviewScore(this.totalReviewScore)
                .totalFreePost(this.totalFreePost)
                .totalFreeComment(this.totalFreeComment)
                .totalRecommendPost(this.totalRecommendPost)
                .totalRecommendComment(this.totalRecommendComment)
                .build();
    }

    public MemberStatistics updateReview(AlbumReview review, boolean isDelete) {
        return MemberStatistics.builder()
                .id(this.id)
                .totalActivityScore(this.totalActivityScore)
                .totalReview(isDelete ? this.totalReview - 1 : this.totalReview + 1)
                .totalReviewScore(isDelete ? this.totalReviewScore - review.getScore() : this.totalReviewScore + review.getScore())
                .totalFreePost(this.totalFreePost)
                .totalFreeComment(this.totalFreeComment)
                .totalRecommendPost(this.totalRecommendPost)
                .totalRecommendComment(this.totalRecommendComment)
                .build();
    }

    public MemberStatistics updateFreePost(boolean isDelete) {
        return MemberStatistics.builder()
                .id(this.id)
                .totalActivityScore(this.totalActivityScore)
                .totalReview(this.totalReview)
                .totalReviewScore(this.totalReviewScore)
                .totalFreePost(isDelete ? this.totalFreePost - 1 : this.totalFreePost + 1)
                .totalFreeComment(this.totalFreeComment)
                .totalRecommendPost(this.totalRecommendPost)
                .totalRecommendComment(this.totalRecommendComment)
                .build();
    }

    public MemberStatistics updateFreeComment(boolean isDelete) {
        return MemberStatistics.builder()
                .id(this.id)
                .totalActivityScore(this.totalActivityScore)
                .totalReview(this.totalReview)
                .totalReviewScore(this.totalReviewScore)
                .totalFreePost(this.totalFreePost)
                .totalFreeComment(isDelete ? this.totalFreeComment - 1 : this.totalFreeComment + 1)
                .totalRecommendPost(this.totalRecommendPost)
                .totalRecommendComment(this.totalRecommendComment)
                .build();
    }

    public MemberStatistics updateRecommendPost(boolean isDelete) {
        return MemberStatistics.builder()
                .id(this.id)
                .totalActivityScore(this.totalActivityScore)
                .totalReview(this.totalReview)
                .totalReviewScore(this.totalReviewScore)
                .totalFreePost(this.totalFreePost)
                .totalFreeComment(this.totalFreeComment)
                .totalRecommendPost(isDelete ? this.totalRecommendPost - 1 : this.totalRecommendPost + 1)
                .totalRecommendComment(this.totalRecommendComment)
                .build();
    }

    public MemberStatistics updateRecommendComment(boolean isDelete) {
        return MemberStatistics.builder()
                .id(this.id)
                .totalActivityScore(this.totalActivityScore)
                .totalReview(this.totalReview)
                .totalReviewScore(this.totalReviewScore)
                .totalFreePost(this.totalFreePost)
                .totalFreeComment(this.totalFreeComment)
                .totalRecommendPost(this.totalRecommendPost)
                .totalRecommendComment(isDelete ? this.totalRecommendComment - 1 : this.totalRecommendComment + 1)
                .build();
    }


}
