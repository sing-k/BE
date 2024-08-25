package com.project.singk.domain.vote.controller.response;

import com.project.singk.domain.vote.domain.VoteType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class VoteResponseTest {

    @Test
    public void VoteType로_VoteResponse를_생성할_수_있다() {
        // given
        VoteType voteType = VoteType.PROS;
        int pros = 1;
        int cons = 0;

        // when
        VoteResponse response = VoteResponse.from(
                pros,
                cons,
                voteType
        );

        // then
        assertAll(
                () -> assertThat(response.getProsCount()).isEqualTo(1),
                () -> assertThat(response.getConsCount()).isEqualTo(0),
                () -> assertThat(response.isPros()).isEqualTo(true),
                () -> assertThat(response.isCons()).isEqualTo(false)
        );
    }
}
