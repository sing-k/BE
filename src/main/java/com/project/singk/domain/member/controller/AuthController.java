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
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/email-authentication/request")
	public BaseResponse<Void> sendAuthenticationCode(@RequestBody @Email String email) {
		authService.sendAuthenticationCode(email);
		return BaseResponse.ok();
	}

}
