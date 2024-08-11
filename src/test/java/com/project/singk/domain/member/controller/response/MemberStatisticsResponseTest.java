package com.project.singk.domain.member.controller.response;

import com.project.singk.domain.member.domain.MemberStatistics;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class MemberStatisticsResponseTest {

    @Test
    public void MemberStatistics로_MemberStatisticsResponse를_생성할_수_있다() {
        // given
        MemberStatistics statistics = MemberStatistics.empty();

        // when
        MemberStatisticsResponse response = MemberStatisticsResponse.from(statistics);

        // then
        assertAll(
                () -> assertThat(response.getAverageReviewScore()).isEqualTo(0.0),
                () -> assertThat(response.getTotalReview()).isEqualTo(0),
                () -> assertThat(response.getTotalActivityScore()).isEqualTo(0),
                () -> assertThat(response.getTotalReviewScore()).isEqualTo(0)
        );
    }
}
