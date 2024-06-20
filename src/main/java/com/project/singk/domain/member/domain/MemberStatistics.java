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

    @Builder
    public MemberStatistics(Long id, int totalActivityScore, int totalReview, int totalReviewScore) {
        this.id = id;
        this.totalActivityScore = totalActivityScore;
        this.totalReview = totalReview;
        this.totalReviewScore = totalReviewScore;
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
                .build();
    }

    public MemberStatistics updateActivity(ActivityHistory activityHistory) {
        return MemberStatistics.builder()
                .id(this.id)
                .totalActivityScore(this.totalActivityScore + activityHistory.getScore())
                .totalReview(this.totalReview)
                .totalReviewScore(this.totalReviewScore)
                .build();
    }

    public MemberStatistics updateReview(AlbumReview albumReview, ActivityHistory activityHistory, boolean isDelete) {
        if (isDelete) {
            return deleteReview(albumReview, activityHistory);
        }

        return createReview(albumReview, activityHistory);
    }

    private MemberStatistics createReview(AlbumReview review, ActivityHistory activityHistory) {
        return MemberStatistics.builder()
                .id(this.id)
                .totalActivityScore(this.totalActivityScore + activityHistory.getScore())
                .totalReview(this.totalReview + 1)
                .totalReviewScore(this.totalReviewScore + review.getScore())
                .build();
    }

    private MemberStatistics deleteReview(AlbumReview review, ActivityHistory activityHistory) {
        return MemberStatistics.builder()
                .id(this.id)
                .totalActivityScore(this.totalActivityScore + activityHistory.getScore())
                .totalReview(this.totalReview - 1)
                .totalReviewScore(this.totalReviewScore - review.getScore())
                .build();
    }
}
