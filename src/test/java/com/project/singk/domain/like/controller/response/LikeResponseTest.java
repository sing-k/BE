package com.project.singk.domain.like.controller.response;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class LikeResponseTest {

    @Test
    public void isLike와_count로_LikeResponse를_생성할_수_있다() {
        // given

        boolean isLike = false;
        int count = 0;
        // when
        LikeResponse response = LikeResponse.from(
                count,
                isLike
        );

        // then
        assertAll(
                () -> assertThat(response.isLike()).isEqualTo(false),
                () -> assertThat(response.getCount()).isEqualTo(0)
        );
    }


}
