package com.project.singk.global.jwt;

import java.io.IOException;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.singk.global.api.AppHttpStatus;
import com.project.singk.global.api.BaseResponse;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtExceptionHandlingFilter extends OncePerRequestFilter {
	private final String CONTENT_TYPE = "application/json;charset=UTF-8";
	private ObjectMapper objectMapper = new ObjectMapper();
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		try {
			filterChain.doFilter(request, response);
		} catch (JwtException e) {
			String body = objectMapper.writeValueAsString(
				BaseResponse.fail(AppHttpStatus.INVALID_TOKEN, e.getMessage()));
			response.getWriter().write(body);
		} catch (OAuth2AuthenticationException e) {
			String body = objectMapper.writeValueAsString(
				BaseResponse.fail(AppHttpStatus.OAUTH_UNAUTHORIZED, e.getMessage()));
			response.getWriter().write(body);
		}
	}
}
