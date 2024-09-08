package com.project.singk.domain.member.controller.response;

import com.project.singk.domain.member.domain.Gender;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.domain.MemberStatistics;
import com.project.singk.domain.member.domain.Role;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class MemberSimpleResponseTest {

    @Test
    public void Member로_MemberSimpleResponse을_생성할_수_있다() {
        // given
        Member member = Member.builder()
                .id(1L)
                .email("singk@gmail.com")
                .password("encodedPassword")
                .name("김철수")
                .nickname("SingK")
                .birthday(LocalDate.of(1999, 12, 30).atStartOfDay())
                .gender(Gender.MALE)
                .role(Role.ROLE_USER)
                .statistics(MemberStatistics.empty())
                .build();

        String imageUrl = "imageUrl";

        // when
        MemberSimpleResponse memberResponse = MemberSimpleResponse.from(member, imageUrl);

        // then
        assertAll(
                () -> assertThat(memberResponse.getId()).isEqualTo(1L),
                () -> assertThat(memberResponse.getImageUrl()).isEqualTo(imageUrl),
                () -> assertThat(memberResponse.getNickname()).isEqualTo("SingK")
        );
    }

}
