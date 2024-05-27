package com.project.singk.domain.member.controller;


import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.singk.domain.member.dto.MemberRequestDto;
import com.project.singk.domain.member.dto.MemberResponseDto;
import com.project.singk.domain.member.service.MemberService;
import com.project.singk.global.api.BaseResponse;
import com.project.singk.global.domain.PkResponseDto;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/me")
	public BaseResponse<MemberResponseDto.Detail> getMember() {
		return BaseResponse.ok(memberService.getMember());
	}

	@PutMapping("/me/nickname")
	public BaseResponse<PkResponseDto> updateNickname(
		@RequestBody MemberRequestDto request
	) {
		return BaseResponse.ok(memberService.updateNickname(request));
	}

	@PutMapping("/me/profile-image")
	public BaseResponse<PkResponseDto> updateProfileImage (
		MultipartFile image
	) {
		return BaseResponse.ok(memberService.updateProfileImage(image));
	}

	@DeleteMapping("/me/profile-image")
	public BaseResponse<Void> deleteProfileImage () {
		memberService.deleteProfileImage();
		return BaseResponse.ok();
	}

}
