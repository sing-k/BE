package com.project.singk.domain.review.controller.response;

import com.project.singk.domain.review.domain.AlbumReviewStatistics;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AlbumReviewStatisticsResponseTest {
    @Test
    public void AlbumReviewStatistics로_AlbumReviewStatisticsResponse을_생성할_수_있다() {
        // given
        AlbumReviewStatistics statistics = AlbumReviewStatistics.builder()
                .totalReviewer(10)
                .totalScore(30)
                .score1Count(2)
                .score2Count(2)
                .score3Count(2)
                .score4Count(2)
                .score5Count(2)
                .maleCount(7)
                .maleTotalScore(21)
                .femaleCount(3)
                .femaleTotalScore(9)
                .build();

        // when
        AlbumReviewStatisticsResponse response = AlbumReviewStatisticsResponse.from(statistics);

        // then
        assertAll(
                () -> assertThat(response.getCount()).isEqualTo(10),
                () -> assertThat(response.getTotalScore()).isEqualTo(30),
                () -> assertThat(response.getAverageScore()).isEqualTo(3.0),
                () -> assertThat(response.getScoreStatistics().get(0).getRatio()).isEqualTo(20.0),
                () -> assertThat(response.getGenderStatistics().get(0).getRatio()).isEqualTo(70.0),
                () -> assertThat(response.getGenderStatistics().get(0).getAverageScore()).isEqualTo(3.0)
        );
    }
}
