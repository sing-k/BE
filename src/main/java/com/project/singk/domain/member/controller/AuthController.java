package com.project.singk.domain.member.controller;


import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.singk.domain.member.controller.port.AuthService;
import com.project.singk.domain.member.domain.MemberCertification;
import com.project.singk.domain.member.domain.MemberCreate;
import com.project.singk.global.api.BaseResponse;
import com.project.singk.global.domain.PkResponseDto;
import com.project.singk.global.domain.TokenDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
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
	public BaseResponse<Void> logout(@Valid @RequestBody TokenDto request) {
		authService.logout(request);
		return BaseResponse.ok();
	}

	@PostMapping("/signup")
	public BaseResponse<PkResponseDto> signup(@Valid @RequestBody MemberCreate request) {
		return BaseResponse.ok(authService.signup(request));
	}

	@PostMapping("/nickname/confirm")
	public BaseResponse<Void> confirmNickname(@RequestBody @Size(max = 12, message = "올바르지 않은 닉네임 형식입니다.") String nickname) {
		authService.confirmNickname(nickname);
		return BaseResponse.ok();
	}

	@PostMapping("/certification/request")
	public BaseResponse<Void> sendAuthenticationCode(@RequestBody @Email(message = "올바르지 않은 이메일 형식입니다.") String email) {
		authService.sendCertificationCode(email);
		return BaseResponse.ok();
	}

	@PostMapping("/certification/confirm")
	public BaseResponse<Void> confirmAuthenticationCode(@Valid @RequestBody MemberCertification request) {
		authService.confirmCertificationCode(request);
		return BaseResponse.ok();
	}

}
