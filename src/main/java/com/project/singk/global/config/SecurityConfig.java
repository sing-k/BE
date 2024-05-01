package com.project.singk.global.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		return http
			// 헤더 설정
			.headers((headers) -> {
				// X-Frame-Options 헤더 설정을 SAME ORIGIN으로 설정하여, 웹 페이지를 iframe으로 삽입하는 공격 방지
				headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin);
			})
			// 세션을 사용하지 않으므로 csrf 공격에 대한 옵션 disable
			.csrf(AbstractHttpConfigurer::disable)
			// CORS 활성화
			.cors((cors) -> {
				cors.configurationSource(corsConfigurationSource());
			})
			// 폼 로그인 비활성화
			.formLogin(AbstractHttpConfigurer::disable)
			// HTTP 인증 비활성화
			.httpBasic(AbstractHttpConfigurer::disable)
			// 인증에 사용할 세션 생성 X
			.sessionManagement((session) -> {
				session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			})
			// API 인가 설정
			.authorizeHttpRequests((authorize) -> {
				authorize.anyRequest().permitAll();
			})
			.build();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		// TODO : Origin 확정 시 변경
		configuration.setAllowedOrigins(List.of("*"));
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE"));
		configuration.setAllowCredentials(true);
		configuration.addExposedHeader("Authorization");
		configuration.addExposedHeader("Refresh");
		configuration.addAllowedHeader("*");
		configuration.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
