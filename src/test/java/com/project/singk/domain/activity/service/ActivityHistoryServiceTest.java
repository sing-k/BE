package com.project.singk.domain.activity.service;

import com.project.singk.domain.activity.controller.response.ActivityGraphResponse;
import com.project.singk.domain.activity.controller.response.ActivityHistoryResponse;
import com.project.singk.domain.activity.domain.ActivityHistory;
import com.project.singk.domain.activity.domain.ActivityType;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.global.api.OffsetPageResponse;
import com.project.singk.mock.TestContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ActivityHistoryServiceTest {

    private TestContainer tc;
    @BeforeEach
    public void init() {
        tc = TestContainer.builder().build();

        List<ActivityHistory> histories = new ArrayList<>();
        LocalDateTime date = LocalDateTime.of(2024, 8, 10, 12, 0, 0);

        Member member = Member.builder()
                .id(1L)
                .build();

        tc.memberRepository.save(member);

        // 2024년 8월 10일을 포함한 100일 전 까지의
        for (int i = 1; i <= 100; i++) {
            histories.add(ActivityHistory.builder()
                    .id((long) i)
                    .type(ActivityType.REACT_ALBUM_REVIEW)
                    .score(ActivityType.REACT_ALBUM_REVIEW.getScore())
                    .createdAt(date)
                    .member(member)
                    .build());
            date = date.minusDays(1);
        }

        histories = tc.activityHistoryRepository.saveAll(histories);
    }

    /**
     * getActivityGraph
     */

    @Test
    public void 일별_활동_그래프를_조회할_수_있다() {
        // given
        Long memberId = 1L;
        String startDate = "2024-08-03";
        String endDate = "2024-08-10";
        String dateType = "DAILY";

        // when
        List<ActivityGraphResponse> response = tc.activityHistoryService.getActivityGraph(
                memberId,
                startDate,
                endDate,
                dateType
        );

        // then
        int size = response.size();
        assertAll(
                () -> assertThat(response.size()).isEqualTo(8),
                () -> assertThat(response.get(size - 1).getDate()).isEqualTo(LocalDate.of(2024, 8, 10)),
                () -> assertThat(response.get(size - 1).getScore()).isEqualTo(500),
                () -> assertThat(response.get(0).getDate()).isEqualTo(LocalDate.of(2024, 8, 3)),
                () -> assertThat(response.get(0).getScore()).isEqualTo(465)
        );
    }

    @Test
    public void 주별_활동_그래프를_조회할_수_있다() {
        // given
        Long memberId = 1L;
        String startDate = "2024-07-19";
        String endDate = "2024-08-10";
        String dateType = "WEEKLY";

        // when
        List<ActivityGraphResponse> response = tc.activityHistoryService.getActivityGraph(
                memberId,
                startDate,
                endDate,
                dateType
        );

        // then
        int size = response.size();
        assertAll(
                () -> assertThat(response.size()).isEqualTo(5),
                () -> assertThat(response.get(size - 1).getDate()).isEqualTo(LocalDate.of(2024, 8, 10)),
                () -> assertThat(response.get(size - 1).getScore()).isEqualTo(500),
                () -> assertThat(response.get(size - 2).getDate()).isEqualTo(LocalDate.of(2024, 8, 4)),
                () -> assertThat(response.get(size - 2).getScore()).isEqualTo(470),
                () -> assertThat(response.get(size - 2).getDate().getDayOfWeek()).isEqualTo(DayOfWeek.SUNDAY),
                () -> assertThat(response.get(0).getDate()).isEqualTo(LocalDate.of(2024, 7, 19)),
                () -> assertThat(response.get(0).getScore()).isEqualTo(390)
        );
    }

    @Test
    public void 월별_활동_그래프를_조회할_수_있다() {
        // given
        Long memberId = 1L;
        String startDate = "2024-05-30";
        String endDate = "2024-08-10";
        String dateType = "MONTHLY";

        // when
        List<ActivityGraphResponse> response = tc.activityHistoryService.getActivityGraph(
                memberId,
                startDate,
                endDate,
                dateType
        );

        // then
        int size = response.size();
        assertAll(
                () -> assertThat(response.size()).isEqualTo(5),
                () -> assertThat(response.get(size - 1).getDate()).isEqualTo(LocalDate.of(2024, 8, 10)),
                () -> assertThat(response.get(size - 1).getScore()).isEqualTo(500),
                () -> assertThat(response.get(size - 2).getDate()).isEqualTo(LocalDate.of(2024, 7, 31)),
                () -> assertThat(response.get(size - 2).getScore()).isEqualTo(450),
                () -> assertThat(response.get(size - 2).getDate().getDayOfMonth()).isEqualTo(Month.JULY.length(true)),
                () -> assertThat(response.get(1).getDate()).isEqualTo(LocalDate.of(2024, 5, 30)),
                () -> assertThat(response.get(1).getScore()).isEqualTo(450),
                () -> assertThat(response.get(1).getDate().getDayOfMonth()).isEqualTo(Month.JULY.length(true)),
                () -> assertThat(response.get(0).getDate()).isEqualTo(LocalDate.of(2024, 5, 30)),
                () -> assertThat(response.get(0).getScore()).isEqualTo(140)
        );
    }

    /**
     * getActivityHistories
     */

    @Test
    public void 자신의_활동_히스토리를_조회할_수_있다() {
        // given
        Long memberId = 1L;
        int offset = 0;
        int limit = 5;
        // when
        OffsetPageResponse<ActivityHistoryResponse> activityHistories = tc.activityHistoryService.getActivityHistories(
                memberId,
                offset,
                limit
        );
        // then
    }
}
