package com.project.singk.domain.member.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import com.project.singk.domain.member.domain.*;
import com.project.singk.global.properties.S3Properties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.project.singk.domain.member.controller.response.MyProfileResponse;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import com.project.singk.global.domain.PkResponseDto;
import com.project.singk.mock.TestContainer;

class MemberServiceTest {

	private TestContainer testContainer;

	@BeforeEach
	public void init() {
		testContainer = TestContainer.builder().build();
		testContainer.memberRepository.save(Member.builder()
			.id(1L)
			.email("singk@gmail.com")
			.password("encodedPassword")
			.name("김철수")
			.nickname("SingK")
			.birthday(LocalDate.of(1999, 12, 30).atStartOfDay())
			.gender(Gender.MALE)
			.role(Role.ROLE_USER)
            .statistics(MemberStatistics.empty())
			.build());
	}

	@Test
	public void 사용자는_자신의_프로필을_조회할_수_있다() {
		// given
		Long loginMemberId = 1L;
		// when
		MyProfileResponse response = testContainer.memberService.getMyProfile(loginMemberId);
		// then
		assertAll(
			() -> assertThat(response.getId()).isEqualTo(1L),
			() -> assertThat(response.getEmail()).isEqualTo("singk@gmail.com"),
			() -> assertThat(response.getImageUrl()).isNull(),
			() -> assertThat(response.getName()).isEqualTo("김철수"),
			() -> assertThat(response.getNickname()).isEqualTo("SingK"),
			() -> assertThat(response.getBirthday()).isEqualTo(LocalDate.of(1999,12,30).atStartOfDay()),
			() -> assertThat(response.getGender()).isEqualTo("남성")
		);
	}

	@Test
	public void 사용자는_자신의_프로필을_수정할_수_있다() {
		// given
		Long loginMemberId = 1L;
		MemberUpdate memberUpdate = MemberUpdate.builder()
			.nickname("newNickname")
            .name("김철수")
            .birthday("1999-12-30")
            .gender("MALE")
			.build();

		// when
		PkResponseDto result = testContainer.memberService.update(
			loginMemberId,
			memberUpdate
		);

		// then
		assertThat(result.getId()).isEqualTo(1L);

	}

	@Test
	public void 변경하려는_닉네임이_존재한다면_사용자는_자신의_프로필을_수정할_수_없다() {
		// given
		Long loginMemberId = 1L;
		MemberUpdate memberUpdate = MemberUpdate.builder()
			.nickname("SingK")
			.build();

		// when
		final ApiException result = assertThrows(ApiException.class,
			() -> testContainer.memberService.update(loginMemberId, memberUpdate));

		// then
		assertThat(result.getStatus()).isEqualTo(AppHttpStatus.DUPLICATE_NICKNAME);
	}


	@Test
	public void 사용자는_프로필_이미지를_업로드할_수_있다() {
		// given
		Long loginMemberId = 1L;
		MockMultipartFile file = new MockMultipartFile(
			"image",
			"test.jpeg",
			MediaType.IMAGE_JPEG_VALUE,
			"test".getBytes()
		);

		// when
		PkResponseDto result = testContainer.memberService.uploadProfileImage(
			loginMemberId,
			file
		);

		// then
		assertThat(result.getId()).isEqualTo(1L);
	}

	@Test
	public void 이미지_파일이_존재하지_않는다면_사용자는_프로필_이미지를_업로드할_수_없다() {
		// given
		Long loginMemberId = 1L;

		// when
		final ApiException result = assertThrows(ApiException.class,
			() -> testContainer.memberService.uploadProfileImage(
				loginMemberId,
				null
		));

		// then
		assertThat(result.getStatus()).isEqualTo(AppHttpStatus.INVALID_FILE);
	}

	@Test
	public void 사용자는_등록한_프로필_이미지가_없다면_프로필_이미지를_삭제할_수_없다() {
		// given
		Long loginMemberId = 1L;

		// when
		final ApiException result = assertThrows(ApiException.class,
			() -> testContainer.memberService.deleteProfileImage(
				loginMemberId
			));

		// then
		assertThat(result.getStatus()).isEqualTo(AppHttpStatus.NOT_FOUND_PROFILE_IMAGE);
	}
}
