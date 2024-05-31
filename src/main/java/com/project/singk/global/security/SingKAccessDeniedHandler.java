package com.project.singk.global.security;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.singk.global.api.AppHttpStatus;
import com.project.singk.global.api.BaseResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SingKAccessDeniedHandler implements AccessDeniedHandler {
	private final String CONTENT_TYPE = "application/json;charset=UTF-8";
	private ObjectMapper objectMapper = new ObjectMapper();
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
		AccessDeniedException accessDeniedException) throws IOException, ServletException {
		response.setContentType(CONTENT_TYPE);
		String body = objectMapper.writeValueAsString(BaseResponse.fail(AppHttpStatus.FORBIDDEN));
		response.getWriter().write(body);
	}
}
