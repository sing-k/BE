package com.project.singk.domain.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.singk.domain.mail.domain.MailDomain;
import com.project.singk.domain.mail.service.MailService;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.dto.AuthCodeRequestDto;
import com.project.singk.domain.member.dto.SignupRequestDto;
import com.project.singk.domain.member.repository.MemberRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import com.project.singk.global.properties.JwtProperties;
import com.project.singk.global.properties.MailProperties;
import com.project.singk.global.domain.PkResponseDto;
import com.project.singk.global.domain.TokenDto;
import com.project.singk.global.jwt.JwtUtil;
import com.project.singk.domain.member.domain.SingKUserDetails;
import com.project.singk.global.util.RedisUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

	private final String AUTH_PREFIX = "AuthCode";
	private final String REFRESH_PREFIX = "Refresh";
	private final MemberRepository memberRepository;
	private final MailService mailService;
	private final RedisUtil redisUtil;
	private final JwtUtil jwtUtil;
	private final JwtProperties jwtProperties;
	private final MailProperties mailProperties;
	private final PasswordEncoder passwordEncoder;


	public void issueAccessToken(HttpServletRequest request, HttpServletResponse response) {
		String clientRefreshToken = jwtUtil.resolveRefreshToken(request);
		Claims refreshClaims = jwtUtil.parseToken(clientRefreshToken);
		String email = REFRESH_PREFIX + refreshClaims.getSubject();
		String serverRefreshToken = redisUtil.getValue(email);

		// 서버에 refresh token이 존재하지 않거나 서버의 refresh token과 클라이언트의 refresh token이 일치하지 않는경우
		if (serverRefreshToken == null || !serverRefreshToken.equals(clientRefreshToken)) {
			throw new ApiException(AppHttpStatus.INVALID_TOKEN);
		}

		Member member = memberRepository.findByEmail(refreshClaims.getSubject())
			.orElseThrow(() -> new ApiException(AppHttpStatus.NOT_FOUND_MEMBER));

		TokenDto token = jwtUtil.generateTokenDto(SingKUserDetails.of(member));

		jwtUtil.setHeaderAccessToken(token.getAccessToken(), response);

		// Refresh Token Rotate
		jwtUtil.setHeaderRefreshToken(token.getRefreshToken(), response);

		redisUtil.deleteValue(email);
		redisUtil.setValue(
			email,
			token.getRefreshToken(),
			jwtProperties.getRefreshExpirationMillis()
		);
	}
	public void logout(TokenDto dto) {
		Claims claims = jwtUtil.parseToken(dto.getRefreshToken());

		// login 성공 시 저장한 Refresh + email 조회
		String email = REFRESH_PREFIX + claims.getSubject();
		String savedRefreshToken = redisUtil.getValue(email);

		if (savedRefreshToken == null) {
			throw new ApiException(AppHttpStatus.UNAUTHORIZED);
		}

		redisUtil.deleteValue(email);
		// Access Token 블랙 리스트 처리
		redisUtil.setValue(
			dto.getAccessToken().substring(7),
			"logout",
			jwtProperties.getAccessExpirationMillis()
		);
	}

	public PkResponseDto signup(SignupRequestDto dto) {
		Member member = memberRepository.findByEmail(dto.getEmail()).orElse(null);
		if (member != null) {
			throw new ApiException(AppHttpStatus.DUPLICATE_MEMBER);
		}

		member = memberRepository.save(dto.toEntity(passwordEncoder));
		return PkResponseDto.of(member.getId());
	}

	public void confirmNickname(String nickname) {
		Member member = memberRepository.findByNickname(nickname).orElse(null);

		if (member != null) {
			throw new ApiException(AppHttpStatus.DUPLICATE_NICKNAME);
		}
	}

	public void sendAuthenticationCode(String email) {
		// 이메일로 조회
		Member member = memberRepository.findByEmail(email).orElse(null);
		if (member != null) {
			throw new ApiException(AppHttpStatus.DUPLICATE_MEMBER);
		}

		// TODO : 더 좋은 방법 찾기
		String code;
		if (MailDomain.GOOGLE.equals(email)) {
			code = mailService.sendGoogleCertificationMail(email, mailProperties.getGoogle().getFrom());
		} else if (MailDomain.NAVER.equals(email)) {
			code = mailService.sendNaverCertificationMail(email, mailProperties.getNaver().getFrom());
		} else {
			throw new ApiException(AppHttpStatus.NOT_SUPPORT_EMAIL_FORMAT);
		}

		// 인증 번호 Redis 저장
		redisUtil.setValue(AUTH_PREFIX + email, code, mailProperties.getExpirationMillis());
	}

	public void confirmAuthenticationCode(AuthCodeRequestDto dto) {
		String serverAuthCode = redisUtil.getValue(AUTH_PREFIX + dto.getEmail());

		// 인증 코드가 만료되거나 일치하지 않는 경우
		if (serverAuthCode == null || !serverAuthCode.equals(dto.getCode())) {
			throw new ApiException(AppHttpStatus.FAILED_VERIFY_CODE);
		}
	}
}
