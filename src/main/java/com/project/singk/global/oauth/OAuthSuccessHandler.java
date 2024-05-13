package com.project.singk.global.oauth;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.project.singk.domain.member.domain.SingKOAuth2User;
import com.project.singk.global.domain.TokenDto;
import com.project.singk.global.jwt.JwtUtil;
import com.project.singk.global.properties.JwtProperties;
import com.project.singk.global.properties.OAuthProperties;
import com.project.singk.global.util.RedisUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	private final String AUTHORIZATION_HEADER = "Authorization";
	private final String REFRESH_HEADER = "Refresh";
	private final JwtUtil jwtUtil;
	private final JwtProperties jwtProperties;
	private final OAuthProperties oAuthProperties;
	private final RedisUtil redisUtil;
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		SingKOAuth2User oAuthUser = (SingKOAuth2User) authentication.getPrincipal();

		if (oAuthUser.isNewbie()) {
			response.addCookie(createCookie("email", oAuthUser.getEmail()));
			response.addCookie(createCookie("name", oAuthUser.getName()));
			response.sendRedirect(oAuthProperties.getUrl().getSignup());
		} else {
			TokenDto token = jwtUtil.generateTokenDto(oAuthUser.getEmail(), oAuthUser.getRole());

			response.addCookie(createCookie(AUTHORIZATION_HEADER, token.getAccessToken()));
			response.addCookie(createCookie(REFRESH_HEADER, token.getRefreshToken()));

			redisUtil.setValue(
				REFRESH_HEADER + oAuthUser.getEmail(),
				token.getRefreshToken(),
				jwtProperties.getRefreshExpirationMillis()
			);

			response.sendRedirect(oAuthProperties.getUrl().getMain());
		}
	}

	private Cookie createCookie(String key, String value) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(oAuthProperties.getCookie().getMaxAge());
		cookie.setPath(oAuthProperties.getCookie().getPath());
		cookie.setHttpOnly(oAuthProperties.getCookie().isHttpOnly());
		cookie.setSecure(oAuthProperties.getCookie().isSecure());
		return cookie;
	}
}
