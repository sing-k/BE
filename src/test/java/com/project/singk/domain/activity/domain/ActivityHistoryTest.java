package com.project.singk.domain.activity.domain;

import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.domain.MemberStatistics;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ActivityHistoryTest {

    @Test
    public void ActivityType과_Member로_ActivityHistory를_만들_수_있다() {
        // given
        ActivityType type = ActivityType.ATTENDANCE;
        Member member = Member.builder()
                .id(1L)
                .email("singk@gmail.com")
                .statistics(MemberStatistics.empty())
                .build();
        // when
        ActivityHistory activityHistory = ActivityHistory.from(type, member);

        // then
        assertAll(
                () -> assertThat(activityHistory.getScore()).isEqualTo(30),
                () -> assertThat(activityHistory.getType()).isEqualTo(ActivityType.ATTENDANCE),
                () -> assertThat(activityHistory.getMember().getEmail()).isEqualTo("singk@gmail.com")
        );
    }
}
