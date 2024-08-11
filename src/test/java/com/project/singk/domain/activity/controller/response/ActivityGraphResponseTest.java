package com.project.singk.domain.activity.controller.response;

import com.project.singk.domain.activity.domain.ActivityScore;
import com.project.singk.domain.activity.domain.ActivityType;
import com.project.singk.domain.member.controller.response.MemberResponse;
import com.project.singk.domain.member.domain.Gender;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.domain.MemberStatistics;
import com.project.singk.domain.member.domain.Role;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ActivityGraphResponseTest {

    @Test
    public void ActivityScore로_ActivityGraphResponse를_생성할_수_있다() {
        // given
        ActivityScore score = ActivityScore.builder()
                .date(LocalDate.of(2024, 8, 10))
                .score(ActivityType.RECOMMENDED_ALBUM_REVIEW.getScore())
                .build();


        // when
        ActivityGraphResponse response = ActivityGraphResponse.from(score);

        // then
        assertAll(
                () -> assertThat(response.getDate()).isEqualTo(LocalDate.of(2024, 8, 10)),
                () -> assertThat(response.getScore()).isEqualTo(50)
        );
    }
}
