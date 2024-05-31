package com.project.singk.global.jwt;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.singk.domain.common.service.port.RedisRepository;
import com.project.singk.domain.member.domain.MemberLogin;
import com.project.singk.domain.member.service.port.JwtRepository;
import com.project.singk.global.api.AppHttpStatus;
import com.project.singk.global.properties.JwtProperties;
import com.project.singk.global.api.BaseResponse;
import com.project.singk.global.domain.TokenDto;
import com.project.singk.global.security.SingKUserDetails;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final String BEARER_PREFIX = "Bearer ";
	private final String AUTHORIZATION_HEADER = "Authorization";
	private final String REFRESH_HEADER = "Refresh";
	private final String CONTENT_TYPE = "application/json;charset=UTF-8";

	private final AuthenticationManager authenticationManager;
	private final RedisRepository redisRepository;
	private final JwtProperties jwtProperties;
	private final JwtRepository jwtRepository;

	@SneakyThrows
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		ObjectMapper objectMapper = new ObjectMapper();
		MemberLogin memberLogin = objectMapper.readValue(request.getInputStream(), MemberLogin.class);

		UsernamePasswordAuthenticationToken authToken =
			new UsernamePasswordAuthenticationToken(memberLogin.getEmail(), memberLogin.getPassword());

		return authenticationManager.authenticate(authToken);
	}

	@Override
	protected void successfulAuthentication(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain chain,
		Authentication authResult
	) {
		SingKUserDetails principal = (SingKUserDetails)authResult.getPrincipal();

		// JWT 생성
		TokenDto token = jwtRepository.generateTokenDto(principal.getId(), principal.getEmail(), principal.getRole());

		// Response Header 설정
		response.setHeader(AUTHORIZATION_HEADER, BEARER_PREFIX + token.getAccessToken());
		response.setHeader(REFRESH_HEADER, token.getRefreshToken());

		// 로그인 성공 시 Refresh Token 저장
		redisRepository.setValue(
			principal.getEmail(),
			token.getRefreshToken(),
			jwtProperties.getRefreshExpirationMillis()
		);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException failed) throws IOException {
		response.setContentType(CONTENT_TYPE);
		ObjectMapper objectMapper = new ObjectMapper();
		String body = objectMapper.writeValueAsString(BaseResponse.fail(AppHttpStatus.UNAUTHORIZED));
		response.getWriter().write(body);
	}
}
