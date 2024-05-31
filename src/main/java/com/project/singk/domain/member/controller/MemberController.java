package com.project.singk.domain.member.controller;


import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.singk.domain.member.controller.port.AuthService;
import com.project.singk.domain.member.controller.port.MemberService;
import com.project.singk.domain.member.domain.MemberUpdate;
import com.project.singk.domain.member.controller.response.MyProfileResponse;
import com.project.singk.global.api.BaseResponse;
import com.project.singk.global.domain.PkResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

	private final MemberService memberService;
	private final AuthService authService;

	@GetMapping("/me")
	public BaseResponse<MyProfileResponse> getMyProfile() {
		return BaseResponse.ok(
			memberService.getMyProfile(
				authService.getLoginMemberId()));
	}

	@PutMapping("/me")
	public BaseResponse<PkResponseDto> update(
		@Valid @RequestBody MemberUpdate request
	) {
		return BaseResponse.ok(
			memberService.update(
				authService.getLoginMemberId(),
				request));
	}

	@PutMapping("/me/profile-image")
	public BaseResponse<PkResponseDto> uploadProfileImage (
		MultipartFile image
	) {
		return BaseResponse.ok(
			memberService.uploadProfileImage(
				authService.getLoginMemberId(),
				image));
	}

	@DeleteMapping("/me/profile-image")
	public BaseResponse<Void> deleteProfileImage () {
		memberService.deleteProfileImage(
			authService.getLoginMemberId()
		);
		return BaseResponse.ok();
	}

}
