package com.project.singk.global.oauth;

import java.io.IOException;

import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.project.singk.global.domain.TokenDto;
import com.project.singk.domain.member.infrastructure.JwtRepositoryImpl;
import com.project.singk.global.properties.JwtProperties;
import com.project.singk.global.properties.OAuthProperties;
import com.project.singk.domain.common.infrastructure.RedisRepositoryImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	private final String AUTHORIZATION_HEADER = "Authorization";
	private final String REFRESH_HEADER = "Refresh";
    private final String BEARER_PREFIX = "Bearer";
    private final String COOKIE_HEADER = "Set-Cookie";
	private final JwtRepositoryImpl jwtRepositoryImpl;
	private final JwtProperties jwtProperties;
	private final OAuthProperties oAuthProperties;
	private final RedisRepositoryImpl redisUtil;
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		SingKOAuth2User oAuthUser = (SingKOAuth2User) authentication.getPrincipal();

        TokenDto token = jwtRepositoryImpl.generateTokenDto(oAuthUser.getId(), oAuthUser.getEmail(), oAuthUser.getRole());

        response.addHeader(COOKIE_HEADER, createCookie(AUTHORIZATION_HEADER, BEARER_PREFIX + token.getAccessToken()));
        response.addHeader(COOKIE_HEADER, createCookie(REFRESH_HEADER, token.getRefreshToken()));

        redisUtil.setValue(
            REFRESH_HEADER + oAuthUser.getEmail(),
            token.getRefreshToken(),
            jwtProperties.getRefreshExpirationMillis()
        );

        if (oAuthUser.isNewbie()) {
            response.sendRedirect(oAuthProperties.getUrl().getMyPage());
        } else {
            response.sendRedirect(oAuthProperties.getUrl().getMain());
        }
	}

    private String createCookie(String key, String value) {
        return ResponseCookie.from(key, value)
                .path(jwtProperties.getCookie().getPath())
                .sameSite(jwtProperties.getCookie().getSameSite())
                .httpOnly(jwtProperties.getCookie().isHttpOnly())
                .secure(jwtProperties.getCookie().isSecure())
                .maxAge(jwtProperties.getCookie().getMaxAge())
                .build()
                .toString();
    }
}
