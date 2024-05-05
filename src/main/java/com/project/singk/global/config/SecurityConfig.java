package com.project.singk.global.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.project.singk.global.config.properties.JwtProperties;
import com.project.singk.global.jwt.JwtAuthenticationFilter;
import com.project.singk.global.jwt.JwtUtil;
import com.project.singk.global.jwt.JwtVerificationFilter;
import com.project.singk.global.util.RedisUtil;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final AuthenticationConfiguration authenticationConfiguration;
	private final RedisUtil redisUtil;
	private final JwtUtil jwtUtil;
	private final JwtProperties jwtProperties;
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
				authorize
					.requestMatchers("/api/auth/**").permitAll()
					.requestMatchers("/api/auth/logout").authenticated()
					.anyRequest().authenticated();
			})
			.addFilterAt(
				jwtAuthenticationFilter(),
				UsernamePasswordAuthenticationFilter.class
			)
			.addFilterAfter(
				new JwtVerificationFilter(
					jwtUtil,
					redisUtil
				),
				JwtAuthenticationFilter.class)
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

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
		JwtAuthenticationFilter filter = new JwtAuthenticationFilter(
			authenticationManager(authenticationConfiguration),
			redisUtil,
			jwtProperties,
			jwtUtil
		);
		filter.setFilterProcessesUrl("/api/auth/login");
		return filter;
	}
}
