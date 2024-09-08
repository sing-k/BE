package com.project.singk.domain.member.controller.response;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import com.project.singk.domain.member.domain.MemberStatistics;
import org.junit.jupiter.api.Test;

import com.project.singk.domain.member.domain.Gender;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.domain.Role;

class MyProfileResponseTest {

	@Test
	public void Member로_MyProfileResponse를_생성할_수_있다() {
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
		MyProfileResponse myProfileResponse = MyProfileResponse.from(member, imageUrl);

		// then
		assertAll(
			() -> assertThat(myProfileResponse.getId()).isEqualTo(1L),
			() -> assertThat(myProfileResponse.getEmail()).isEqualTo("singk@gmail.com"),
			() -> assertThat(myProfileResponse.getNickname()).isEqualTo("SingK"),
			() -> assertThat(myProfileResponse.getName()).isEqualTo("김철수"),
			() -> assertThat(myProfileResponse.getBirthday()).isEqualTo("1999-12-30T00:00"),
			() -> assertThat(myProfileResponse.getGender()).isEqualTo("남성"),
			() -> assertThat(myProfileResponse.getStatistics().getTotalActivityScore()).isEqualTo(0)
		);
	}
}
