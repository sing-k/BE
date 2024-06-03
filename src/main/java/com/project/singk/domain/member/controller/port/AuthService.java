package com.project.singk.domain.member.controller.port;

import com.project.singk.domain.member.domain.MemberCertification;
import com.project.singk.domain.member.domain.MemberCreate;
import com.project.singk.global.domain.PkResponseDto;
import com.project.singk.global.domain.TokenDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
	Long getLoginMemberId();
	void issueAccessToken(HttpServletRequest request, HttpServletResponse response);
	public void logout(TokenDto dto);
	PkResponseDto signup(MemberCreate memberCreate);
	void confirmNickname(String nickname);
	void sendCertificationCode(String email);
	void confirmCertificationCode(MemberCertification memberCertification);
}
