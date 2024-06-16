package com.project.singk.domain.review.domain;

import com.project.singk.domain.member.domain.Gender;
import com.project.singk.domain.member.domain.Member;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AlbumReviewStatisticsTest {

    @Test
    public void 비어있는_AlbumReviewStatistic를_생성할_수_있다() {
        // given

        // when
        AlbumReviewStatistics statistics = AlbumReviewStatistics.empty();

        // then
        assertAll(
                () -> assertThat(statistics.getTotalReviewer()).isEqualTo(0),
                () -> assertThat(statistics.getTotalScore()).isEqualTo(0),
                () -> assertThat(statistics.getAverageScore()).isEqualTo(0.0),
                () -> assertThat(statistics.getScore1Count()).isEqualTo(0),
                () -> assertThat(statistics.getScore2Count()).isEqualTo(0),
                () -> assertThat(statistics.getScore3Count()).isEqualTo(0),
                () -> assertThat(statistics.getScore4Count()).isEqualTo(0),
                () -> assertThat(statistics.getScore5Count()).isEqualTo(0),
                () -> assertThat(statistics.getMaleCount()).isEqualTo(0),
                () -> assertThat(statistics.getMaleTotalScore()).isEqualTo(0),
                () -> assertThat(statistics.getFemaleCount()).isEqualTo(0),
                () -> assertThat(statistics.getFemaleTotalScore()).isEqualTo(0)
        );
    }

    @Test
    public void AlbumReviewStatistic는_Member와_AlbumReview로_통계_데이터를_추가할_수_있다() {
        // given
        AlbumReviewStatistics statistics = AlbumReviewStatistics.empty();

        Member reviewer = Member.builder()
                .gender(Gender.MALE)
                .build();

        AlbumReview review = AlbumReview.builder()
                .score(5)
                .build();
        // when
        statistics = statistics.update(reviewer, review, false);

        // then
        assertThat(statistics.getTotalReviewer()).isEqualTo(1);
        assertThat(statistics.getTotalScore()).isEqualTo(5);
        assertThat(statistics.getAverageScore()).isEqualTo(5.0);
        assertThat(statistics.getScore1Count()).isEqualTo(0);
        assertThat(statistics.getScore2Count()).isEqualTo(0);
        assertThat(statistics.getScore3Count()).isEqualTo(0);
        assertThat(statistics.getScore4Count()).isEqualTo(0);
        assertThat(statistics.getScore5Count()).isEqualTo(1);
        assertThat(statistics.getMaleCount()).isEqualTo(1);
        assertThat(statistics.getMaleTotalScore()).isEqualTo(5);
        assertThat(statistics.getFemaleCount()).isEqualTo(0);
        assertThat(statistics.getFemaleTotalScore()).isEqualTo(0);
    }

    @Test
    public void AlbumReviewStatistic는_Member와_AlbumReview로_통계를_삭제할_수_있다() {
        // given
        AlbumReviewStatistics statistics = AlbumReviewStatistics.builder()
                .totalReviewer(1)
                .totalScore(5)
                .averageScore(5.0)
                .score1Count(0)
                .score2Count(0)
                .score3Count(0)
                .score4Count(0)
                .score5Count(1)
                .maleCount(1)
                .maleTotalScore(5)
                .femaleCount(0)
                .femaleTotalScore(0)
                .build();



        Member reviewer = Member.builder()
                .gender(Gender.MALE)
                .build();

        AlbumReview review = AlbumReview.builder()
                .score(5)
                .build();
        // when
            statistics = statistics.update(reviewer, review, true);

        // then
        assertThat(statistics.getTotalReviewer()).isEqualTo(0);
        assertThat(statistics.getTotalScore()).isEqualTo(0);
        assertThat(statistics.getAverageScore()).isEqualTo(0.0);
        assertThat(statistics.getScore1Count()).isEqualTo(0);
        assertThat(statistics.getScore2Count()).isEqualTo(0);
        assertThat(statistics.getScore3Count()).isEqualTo(0);
        assertThat(statistics.getScore4Count()).isEqualTo(0);
        assertThat(statistics.getScore5Count()).isEqualTo(0);
        assertThat(statistics.getMaleCount()).isEqualTo(0);
        assertThat(statistics.getMaleTotalScore()).isEqualTo(0);
        assertThat(statistics.getFemaleCount()).isEqualTo(0);
        assertThat(statistics.getFemaleTotalScore()).isEqualTo(0);
    }

}
