package com.project.singk.global.oauth;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.singk.global.api.AppHttpStatus;
import com.project.singk.global.api.BaseResponse;
import com.project.singk.global.properties.OAuthProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class OAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private final OAuthProperties oAuthProperties;
    private final String CONTENT_TYPE = "application/json;charset=UTF-8";
    private ObjectMapper objectMapper = new ObjectMapper();
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws IOException, ServletException {

        response.setContentType(CONTENT_TYPE);
        String body = objectMapper.writeValueAsString(
                BaseResponse.fail(AppHttpStatus.FAILED_AUTHENTICATION_OAUTH, exception.getMessage()));
        response.getWriter().write(body);

		response.sendRedirect(oAuthProperties.getUrl().getLogin());
	}
}
