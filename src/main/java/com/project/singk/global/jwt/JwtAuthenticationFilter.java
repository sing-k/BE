package com.project.singk.global.jwt;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.singk.domain.member.dto.LoginRequestDto;
import com.project.singk.global.api.AppHttpStatus;
import com.project.singk.global.properties.JwtProperties;
import com.project.singk.global.api.BaseResponse;
import com.project.singk.global.domain.TokenDto;
import com.project.singk.domain.member.domain.SingKUserDetails;
import com.project.singk.global.util.RedisUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final String REFRESH_PREFIX = "Refresh";
	private final String CONTENT_TYPE = "application/json;charset=UTF-8";

	private final AuthenticationManager authenticationManager;
	private final RedisUtil redisUtil;
	private final JwtProperties jwtProperties;
	private final JwtUtil jwtUtil;

	@SneakyThrows
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

		ObjectMapper objectMapper = new ObjectMapper();
		LoginRequestDto loginRequestDto = objectMapper.readValue(request.getInputStream(), LoginRequestDto.class);

		UsernamePasswordAuthenticationToken authToken =
			new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword());

		return authenticationManager.authenticate(authToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) {
		SingKUserDetails principal = (SingKUserDetails)authResult.getPrincipal();

		// JWT 생성
		TokenDto token = jwtUtil.generateTokenDto(principal.getEmail(), principal.getRole());

		// Response Header 설정
		jwtUtil.setHeaderAccessToken(token.getAccessToken(), response);
		jwtUtil.setHeaderRefreshToken(token.getAccessToken(), response);

		// 로그인 성공 시 Refresh Token 저장
		redisUtil.setValue(
			REFRESH_PREFIX + principal.getEmail(),
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
