package com.project.singk.global.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.singk.global.api.AppHttpStatus;
import com.project.singk.global.api.BaseResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SingKAuthenticationEntryPoint implements AuthenticationEntryPoint {
	private final String CONTENT_TYPE = "application/json;charset=UTF-8";
	private ObjectMapper objectMapper = new ObjectMapper();
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException, ServletException {
		response.setContentType(CONTENT_TYPE);
		String body = objectMapper.writeValueAsString(BaseResponse.fail(AppHttpStatus.UNAUTHORIZED));
		response.getWriter().write(body);
	}
}
