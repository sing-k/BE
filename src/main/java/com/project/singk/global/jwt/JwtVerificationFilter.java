package com.project.singk.global.jwt;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.project.singk.global.api.AppHttpStatus;
import com.project.singk.global.util.RedisUtil;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {
	private final List<String> EXCLUDE_URL = List.of(
		"/",
		"/api/auth/email-authentication/request",
		"/api/auth/email-authentication/confirm",
		"/api/auth/nickname/confirm",
		"/api/auth/signup",
		"/api/auth/login",
		"/api/auth/access-token"
	);
	private final JwtUtil jwtUtil;
	private final RedisUtil redisUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String accessToken = jwtUtil.resolveAccessToken(request);

		if (!StringUtils.hasText(accessToken)) {
			filterChain.doFilter(request, response);
			return;
		}

		if (isLogout(accessToken)) {
			throw new JwtException(AppHttpStatus.BLOCKED_TOKEN.getMessage());
		}

		jwtUtil.parseToken(accessToken);
		setAuthenticationToSecurityContextHolder(accessToken);
		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return EXCLUDE_URL.stream()
			.anyMatch(exclude -> exclude.equalsIgnoreCase(request.getServletPath()));
	}


	public boolean isLogout(String accessToken) {
		return redisUtil.getValue(accessToken) != null;
	}
	private void setAuthenticationToSecurityContextHolder(String accessToken) {
		Authentication authentication = jwtUtil.getAuthentication(accessToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
