package com.project.singk.domain.member.controller;

import java.util.Map;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.singk.domain.member.dto.AuthCodeRequestDto;
import com.project.singk.domain.member.service.AuthService;
import com.project.singk.global.api.BaseResponse;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/nickname/confirm")
	public BaseResponse<Void> confirmNickname(@RequestBody @Size(max = 12) String nickname) {
		authService.confirmNickname(nickname);
		return BaseResponse.ok();
	}

	@PostMapping("/email-authentication/request")
	public BaseResponse<Void> sendAuthenticationCode(@RequestBody @Email String email) {
		authService.sendAuthenticationCode(email);
		return BaseResponse.ok();
	}

	@PostMapping("/email-authentication/confirm")
	public BaseResponse<Void> confirmAuthenticationCode(@RequestBody AuthCodeRequestDto request) {
		authService.confirmAuthenticationCode(request);
		return BaseResponse.ok();
	}

}
