package com.project.singk.domain.activity.domain;

import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.domain.MemberStatistics;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ActivityScoreTest {

    @Test
    public void score과_date로_ActivityScore를_만들_수_있다() {
        // given
        int score = 450;
        LocalDate date = LocalDate.of(2024, 8, 10);

        // when
        ActivityScore activityScore = ActivityScore.from(score, date);

        // then
        assertAll(
                () -> assertThat(activityScore.getScore()).isEqualTo(450),
                () -> assertThat(activityScore.getDate()).isEqualTo(LocalDate.of(2024, 8, 10))
        );
    }
}
