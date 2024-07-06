package com.project.singk.global.jwt;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.project.singk.domain.common.service.port.RedisRepository;
import com.project.singk.domain.member.service.port.JwtRepository;
import com.project.singk.global.api.AppHttpStatus;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {

	private final JwtRepository jwtRepository;
	private final RedisRepository redisRepository;

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws ServletException, IOException {
		String accessToken = jwtRepository.resolveAccessToken(request);

		// Access Token 없으면 다음 필터로 넘어가기
		if (!StringUtils.hasText(accessToken)) {
			filterChain.doFilter(request, response);
			return;
		}

		if (isLogout(accessToken)) {
			throw new JwtException(AppHttpStatus.BLOCKED_TOKEN.getMessage());
		}

		jwtRepository.parseToken(accessToken);

		setAuthenticationToSecurityContextHolder(accessToken);
		filterChain.doFilter(request, response);
	}

	private boolean isLogout(String accessToken) {
		return redisRepository.getValue(accessToken) != null;
	}
	private void setAuthenticationToSecurityContextHolder(String accessToken) {
		Authentication authentication = jwtRepository.getAuthentication(accessToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
