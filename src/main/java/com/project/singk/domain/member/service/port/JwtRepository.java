package com.project.singk.domain.member.service.port;

import org.springframework.security.core.Authentication;

import com.project.singk.domain.member.domain.Role;
import com.project.singk.global.domain.TokenDto;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface JwtRepository {
	TokenDto generateTokenDto(Long id, String email, Role role);
	Claims parseToken(String token);
	Authentication getAuthentication(String accessToken);
	String resolveAccessToken(HttpServletRequest request);
	String resolveRefreshToken(HttpServletRequest request);
}
