package com.project.singk.domain.activity.controller.response;

import com.project.singk.domain.activity.domain.ActivityHistory;
import com.project.singk.domain.activity.domain.ActivityScore;
import com.project.singk.domain.activity.domain.ActivityType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ActivityHistoryResponseTest {

    @Test
    public void ActivityHistory로_ActivityGraphResponse를_생성할_수_있다() {
        // given
        ActivityHistory score = ActivityHistory.builder()
                .id(1L)
                .type(ActivityType.RECOMMENDED_ALBUM_REVIEW)
                .score(ActivityType.RECOMMENDED_ALBUM_REVIEW.getScore())
                .createdAt(LocalDate.of(2024, 8, 10).atStartOfDay())
                .build();

        // when
        ActivityHistoryResponse response = ActivityHistoryResponse.from(score);

        // then
        assertAll(
                () -> assertThat(response.getContent()).isEqualTo("앨범 감상평 공감받기"),
                () -> assertThat(response.getScore()).isEqualTo(50),
                () -> assertThat(response.getDate()).isEqualTo(LocalDateTime.of(2024, 8, 10, 0, 0, 0))
        );
    }
}
