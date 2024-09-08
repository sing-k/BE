package com.project.singk.domain.member.domain;


import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.project.singk.mock.FakePasswordEncoderHolder;

class MemberTest {

	@Test
	public void MemberCreate로_Member를_생성할_수_있다() {
		// given
		MemberCreate memberCreate = MemberCreate.builder()
			.email("singk@gmail.com")
			.password("qwer1234")
			.name("김철수")
			.nickname("SingK")
			.birthday("1999-12-30")
			.gender("MALE")
			.build();

        MemberStatistics statistics = MemberStatistics.empty();

		// when
		Member member = Member.from(
                memberCreate,
                statistics,
                new FakePasswordEncoderHolder("encodedPassword")
        );

		// then
		assertAll(
			() -> assertThat(member.getId()).isNull(),
			() -> assertThat(member.getEmail()).isEqualTo("singk@gmail.com"),
			() -> assertThat(member.getPassword()).isEqualTo("encodedPassword"),
			() -> assertThat(member.getNickname()).isEqualTo("SingK"),
			() -> assertThat(member.getName()).isEqualTo("김철수"),
			() -> assertThat(member.getBirthday()).isEqualTo(LocalDate.of(1999, 12, 30).atStartOfDay()),
			() -> assertThat(member.getGender()).isEqualTo(Gender.MALE),
			() -> assertThat(member.getRole()).isEqualTo(Role.ROLE_USER)
		);
	}

	@Test
	public void MemberUpdate로_Member를_수정할_수_있다() {
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
			.build();

		MemberUpdate memberUpdate = MemberUpdate.builder()
			.nickname("NewNickname")
            .gender("FEMALE")
            .name("김영희")
            .birthday("2000-01-01")
			.build();

		// when
		member = member.update(memberUpdate);

		// then
		assertThat(member.getId()).isEqualTo(1L);
		assertThat(member.getEmail()).isEqualTo("singk@gmail.com");
		assertThat(member.getPassword()).isEqualTo("encodedPassword");
		assertThat(member.getNickname()).isEqualTo("NewNickname");
		assertThat(member.getName()).isEqualTo("김영희");
		assertThat(member.getBirthday()).isEqualTo(LocalDate.of(2000, 1, 1).atStartOfDay());
		assertThat(member.getGender()).isEqualTo(Gender.FEMALE);
		assertThat(member.getRole()).isEqualTo(Role.ROLE_USER);
	}

	@Test
	void Member는_프로필_이미지를_수정할_수_있다() {
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
			.build();

		// when
		member = member.uploadProfileImage("key");

		// then
		assertThat(member.getId()).isEqualTo(1L);
		assertThat(member.getEmail()).isEqualTo("singk@gmail.com");
		assertThat(member.getPassword()).isEqualTo("encodedPassword");
		assertThat(member.getNickname()).isEqualTo("SingK");
		assertThat(member.getImageUrl()).isEqualTo("key");
		assertThat(member.getName()).isEqualTo("김철수");
		assertThat(member.getBirthday()).isEqualTo(LocalDate.of(1999, 12, 30).atStartOfDay());
		assertThat(member.getGender()).isEqualTo(Gender.MALE);
		assertThat(member.getRole()).isEqualTo(Role.ROLE_USER);
	}

	@Test
	void Member는_프로필_이미지를_삭제할_수_있다() {
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
			.build();

		// when
		member = member.uploadProfileImage(null);

		// then
		assertThat(member.getId()).isEqualTo(1L);
		assertThat(member.getEmail()).isEqualTo("singk@gmail.com");
		assertThat(member.getPassword()).isEqualTo("encodedPassword");
		assertThat(member.getNickname()).isEqualTo("SingK");
		assertThat(member.getImageUrl()).isNull();
		assertThat(member.getName()).isEqualTo("김철수");
		assertThat(member.getBirthday()).isEqualTo(LocalDate.of(1999, 12, 30).atStartOfDay());
		assertThat(member.getGender()).isEqualTo(Gender.MALE);
		assertThat(member.getRole()).isEqualTo(Role.ROLE_USER);
	}

    @Test
    public void Member는_MemberStatistics를_수정할_수_있다() {
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
                .build();

        MemberStatistics statistics = MemberStatistics.builder()
                .totalActivityScore(50)
                .totalReview(1)
                .totalReviewScore(5)
                .build();

        // when
        member = member.updateStatistic(statistics);

        // then

        assertThat(member.getStatistics()).isNotNull();
        assertThat(member.getStatistics().getTotalActivityScore()).isEqualTo(50);
        assertThat(member.getStatistics().getTotalReview()).isEqualTo(1);
        assertThat(member.getStatistics().getTotalReviewScore()).isEqualTo(5);
    }
}
