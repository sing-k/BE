package com.project.singk.global.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import com.project.singk.global.config.properties.JwtProperties;
import com.project.singk.global.domain.TokenDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtUtil {

	private final String BEARER_PREFIX = "Bearer ";
	private final String AUTHORIZATION_HEADER = "Authorization";
	private final String REFRESH_HEADER = "Refresh";
	private final JwtProperties jwtProperties;
	private SecretKey key;

	@PostConstruct
	public void init() {
		String base64EncodedSecretKey = encodeBase64(jwtProperties.getSecretKey());
		this.key = getSecretKeyFromBase64EncodedKey(base64EncodedSecretKey);
	}

	private String encodeBase64(String target) {
		return Encoders.BASE64.encode(target.getBytes(StandardCharsets.UTF_8));
	}

	// HS256 (HMAC with SHA-256)
	private SecretKey getSecretKeyFromBase64EncodedKey(String key) {
		byte[] keyBytes = Decoders.BASE64.decode(key);
		return new SecretKeySpec(keyBytes, Jwts.SIG.HS256.key().build().getAlgorithm());
	}

	// 액세스 토큰, 리프레쉬 토큰 생성
	public TokenDto generateTokenDto(SingKUserDetails userDetails) {
		Date issuedAt = new Date(System.currentTimeMillis());

		String accessToken = Jwts.builder()
			.claims(generatePublicClaims(userDetails))
			.subject(userDetails.getEmail())
			.expiration(getTokenExpiration(jwtProperties.getAccessExpirationMillis()))
			.issuedAt(issuedAt)
			.signWith(key)
			.compact();

		String refreshToken = Jwts.builder()
			.subject(userDetails.getEmail())
			.expiration(getTokenExpiration(jwtProperties.getRefreshExpirationMillis()))
			.issuedAt(issuedAt)
			.signWith(key)
			.compact();

		return TokenDto.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	private Date getTokenExpiration(long expirationMillis) {
		return new Date(new Date().getTime() + expirationMillis);
	}

	// 공개 클레임
	private Map<String, String> generatePublicClaims(SingKUserDetails userDetails) {
		Map<String, String> claims = new HashMap<>();
		claims.put("role", userDetails.getRole().name());
		return claims;
	}

	// JWT 토큰 복호화 및 검증
	public Claims parseToken(String token) {
		try {
			return Jwts.parser()
				.verifyWith(key)
				.build()
				.parseSignedClaims(token)
				.getPayload();
		} catch (MalformedJwtException e) {
			throw new ApiException(AppHttpStatus.MALFORMED_TOKEN);
		} catch (ExpiredJwtException e) {
			throw new ApiException(AppHttpStatus.EXPIRED_TOKEN);
		} catch (UnsupportedJwtException e) {
			throw new ApiException(AppHttpStatus.UNSUPPORTED_TOKEN);
		} catch (Exception e) {
			throw new ApiException(AppHttpStatus.INVALID_TOKEN);
		}
	}

	// JWT 토큰으로 Authentication
	public Authentication getAuthentication(String accessToken) {
		Claims claims = parseToken(accessToken);
		String role = claims.get("role").toString();

		if (role == null) {
			throw new ApiException(AppHttpStatus.INVALID_TOKEN);
		}

		SingKUserDetails userDetails = SingKUserDetails.of(
			claims.getSubject(),
			role
		);

		return new UsernamePasswordAuthenticationToken(
			userDetails,
			null,
			userDetails.getAuthorities()
		);
	}

	public String resolveAccessToken(HttpServletRequest request) {
		String accessToken = request.getHeader(AUTHORIZATION_HEADER);
		if (StringUtils.hasText(accessToken) && accessToken.startsWith(BEARER_PREFIX)) {
			return accessToken.substring(7);
		}

		return null;
	}

	public void setHeaderAccessToken(String accessToken, HttpServletResponse response) {
		response.setHeader(AUTHORIZATION_HEADER, BEARER_PREFIX + accessToken);
	}

	public String resolveRefreshToken(HttpServletRequest request) {
		String refreshToken = request.getHeader(REFRESH_HEADER);
		if (StringUtils.hasText(refreshToken)) {
			return refreshToken;
		}

		return null;
	}

	public void setHeaderRefreshToken(String refreshToken, HttpServletResponse response) {
		response.setHeader(REFRESH_HEADER, refreshToken);
	}
}
