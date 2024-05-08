package com.project.singk.global.jwt;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.BaseResponse;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtExceptionHandlingFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			filterChain.doFilter(request, response);
		} catch (ApiException e) {
			response.setContentType("application/json;charset=UTF-8");
			String body = objectMapper.writeValueAsString(BaseResponse.fail(e));
			response.getWriter().write(objectMapper.writeValueAsString(body));
		}
	}
}
