package com.project.singk.domain.member.domain;

import com.project.singk.domain.activity.domain.ActivityHistory;
import com.project.singk.domain.activity.domain.ActivityType;
import com.project.singk.domain.review.domain.AlbumReview;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class MemberStatisticsTest {

    @Test
    public void 비어있는_MemberStatistic를_생성할_수_있다() {
        // given

        // when
        MemberStatistics statistics = MemberStatistics.empty();

        // then
        assertAll(
                () -> assertThat(statistics.getTotalActivityScore()).isEqualTo(0),
                () -> assertThat(statistics.getTotalReview()).isEqualTo(0),
                () -> assertThat(statistics.getTotalReviewScore()).isEqualTo(0)
        );
    }

    @Test
    public void ActivityHistory를_받아서_MemberStatistics에_반영할_수_있다() {
        // given
        MemberStatistics statistics = MemberStatistics.empty();

        ActivityHistory activityHistory = ActivityHistory.builder()
                .type(ActivityType.ATTENDANCE)
                .score(ActivityType.ATTENDANCE.getScore())
                .build();
        // when
        statistics = statistics.updateActivity(activityHistory);

        // then
        assertThat(statistics.getTotalActivityScore()).isEqualTo(30);
        assertThat(statistics.getTotalReview()).isEqualTo(0);
        assertThat(statistics.getTotalReviewScore()).isEqualTo(0);
    }

    @Test
    public void AlbumReview를_받아서_MemberStatistics에_추가할_수_있다() {
        // given
        MemberStatistics statistics = MemberStatistics.empty();

        AlbumReview review = AlbumReview.builder()
                .score(5)
                .build();

        // when
        statistics = statistics.updateReview(review, false);

        // then
        assertThat(statistics.getTotalReview()).isEqualTo(1);
        assertThat(statistics.getTotalReviewScore()).isEqualTo(5);
    }
    @Test
    public void AlbumReview와_ActivityHistory를_받아서_MemberStatistics에_삭제할_수_있다() {
        // given
        MemberStatistics statistics = MemberStatistics.builder()
                .totalActivityScore(10)
                .totalReview(1)
                .totalReviewScore(5)
                .build();

        AlbumReview review = AlbumReview.builder()
                .score(5)
                .build();

        // when
        statistics = statistics.updateReview(review, true);

        // then
        assertThat(statistics.getTotalReview()).isEqualTo(0);
        assertThat(statistics.getTotalReviewScore()).isEqualTo(0);
    }

    @Test
    public void MemberStatistics는_평균을_계산할_수_있다() {
        // given
        int a = 25;
        int b = 5;

        // when
        double average = MemberStatistics.empty().calculateAverage(a, b);

        // then
        assertThat(average).isEqualTo(5.0);
    }
}
