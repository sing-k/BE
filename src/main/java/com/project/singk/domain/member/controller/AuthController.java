package com.project.singk.domain.member.controller;


import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.singk.domain.member.dto.AuthCodeRequestDto;
import com.project.singk.domain.member.dto.SignupRequestDto;
import com.project.singk.domain.member.service.AuthService;
import com.project.singk.global.api.BaseResponse;
import com.project.singk.global.domain.PkResponseDto;
import com.project.singk.global.domain.TokenDto;
import com.project.singk.global.validator.Email;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/access-token")
	public BaseResponse<Void> issueAccessToken(HttpServletRequest request, HttpServletResponse response) {
		authService.issueAccessToken(request, response);
		return BaseResponse.ok();
	}
	@PostMapping("/logout")
	public BaseResponse<Void> logout(@RequestBody TokenDto request) {
		authService.logout(request);
		return BaseResponse.ok();
	}

	@PostMapping("/signup")
	public BaseResponse<PkResponseDto> signup(@RequestBody SignupRequestDto request) {
		return BaseResponse.ok(authService.signup(request));
	}

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
