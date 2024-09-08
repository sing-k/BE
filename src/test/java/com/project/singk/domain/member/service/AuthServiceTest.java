package com.project.singk.domain.member.service;


import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.singk.domain.member.domain.Gender;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.domain.MemberCertification;
import com.project.singk.domain.member.domain.MemberCreate;
import com.project.singk.domain.member.domain.Role;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import com.project.singk.global.domain.PkResponseDto;
import com.project.singk.global.properties.MailProperties;
import com.project.singk.mock.TestContainer;

class AuthServiceTest {

	@Test
	public void MemberCreate로_사용자를_생성할_수_있다() {
		// given
		TestContainer testContainer = TestContainer.builder().build();
		MemberCreate memberCreate = MemberCreate.builder()
			.email("singk@gmail.com")
			.password("qwer1234")
			.name("김철수")
			.nickname("SingK")
			.birthday("1999-12-30")
			.gender("MALE")
			.build();

		// when
		PkResponseDto result = testContainer.authService.signup(memberCreate);

		// then
		assertThat(result.getId()).isNotNull();
	}

	@Test
	public void 회원가입을_할때_이미_가입된_이메일이_존재_한다면_사용자를_생성할_수_없다() {
		// given
		TestContainer testContainer = TestContainer.builder().build();
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
		member = testContainer.memberRepository.save(member);

		MemberCreate memberCreate = MemberCreate.builder()
			.email("singk@gmail.com")
			.password("qwer1234")
			.name("김철수")
			.nickname("SingKABC")
			.birthday("1999-12-30")
			.gender("MALE")
			.build();

		// when
		final ApiException result = assertThrows(ApiException.class,
			() -> testContainer.authService.signup(memberCreate));

		// then
		assertThat(result.getStatus()).isEqualTo(AppHttpStatus.DUPLICATE_MEMBER);
	}

	@Test
	public void 닉네임_중복_체크를_할때_이미_가입된_닉네임이_있으면_예외가_발생한다() {

		// given
		TestContainer testContainer = TestContainer.builder().build();
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
		member = testContainer.memberRepository.save(member);

		// when
		final ApiException result = assertThrows(ApiException.class,
			() -> testContainer.authService.confirmNickname("SingK"));

		// then
		assertThat(result.getStatus()).isEqualTo(AppHttpStatus.DUPLICATE_NICKNAME);
	}

	@Test
	public void 이미_가입된_이메일이_있으면_인증코드전송을_요청할_수_없다() {
		// given
		TestContainer testContainer = TestContainer.builder().build();
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
		member = testContainer.memberRepository.save(member);

		// when
		final ApiException result = assertThrows(ApiException.class,
			() -> testContainer.authService.sendCertificationCode("singk@gmail.com"));

		// then
		assertThat(result.getStatus()).isEqualTo(AppHttpStatus.DUPLICATE_MEMBER);
	}

	@Test
	public void 인증코드가_일치하지_않으면_예외가_발생한다() {
		// given
		TestContainer testContainer = TestContainer.builder().build();
		MemberCertification memberCertification = MemberCertification.builder()
			.email("singk@gmail.com")
			.code("123456")
			.build();
		testContainer.redisRepository.setValue("singk@gmail.com", "654321", 0L);
		// when

		final ApiException result = assertThrows(ApiException.class,
			() ->  testContainer.authService.confirmCertificationCode(memberCertification));

		// then
		assertThat(result.getStatus()).isEqualTo(AppHttpStatus.FAILED_VERIFY_CERTIFICATION_CODE);
	}
}
