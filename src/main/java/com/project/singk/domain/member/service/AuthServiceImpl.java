package com.project.singk.domain.member.service;

import com.project.singk.domain.member.domain.MemberStatistics;
import jakarta.servlet.http.Cookie;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.project.singk.domain.common.service.port.RedisRepository;
import com.project.singk.domain.member.controller.port.AuthService;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.domain.MemberCertification;
import com.project.singk.domain.member.domain.MemberCreate;
import com.project.singk.domain.member.service.port.CodeGenerator;
import com.project.singk.domain.member.service.port.JwtRepository;
import com.project.singk.domain.member.service.port.MemberRepository;
import com.project.singk.domain.member.service.port.PasswordEncoderHolder;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import com.project.singk.global.properties.JwtProperties;
import com.project.singk.global.properties.MailProperties;
import com.project.singk.global.domain.PkResponseDto;
import com.project.singk.global.domain.TokenDto;
import com.project.singk.global.security.SingKUserDetails;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Service
@Builder
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {
	private final String BEARER_PREFIX = "Bearer";
	private final String AUTHORIZATION_HEADER = "Authorization";
	private final String REFRESH_HEADER = "Refresh";
	private final String AUTH_PREFIX = "AuthCode";
	private final MemberRepository memberRepository;
	private final CodeGenerator codeGenerator;
	private final MailService mailService;
	private final RedisRepository redisRepository;
	private final JwtRepository jwtRepository;
	private final JwtProperties jwtProperties;
	private final MailProperties mailProperties;
	private final PasswordEncoderHolder passwordEncoderHolder;

	@Override
	public Long getLoginMemberId() {
		SingKUserDetails userDetails = (SingKUserDetails) SecurityContextHolder
			.getContext()
			.getAuthentication()
			.getPrincipal();

		return userDetails == null ? null : userDetails.getId();
	}

	@Override
	public void issueAccessToken(HttpServletRequest request, HttpServletResponse response) {
		String clientRefreshToken = jwtRepository.resolveRefreshToken(request);
		Claims refreshClaims = jwtRepository.parseToken(clientRefreshToken);
		String clientEmail = refreshClaims.getSubject();
		String serverRefreshToken = redisRepository.getValue(clientEmail);

		// 서버에 refresh token이 존재하지 않거나 서버의 refresh token과 클라이언트의 refresh token이 일치하지 않는경우
		if (serverRefreshToken == null || !serverRefreshToken.equals(clientRefreshToken)) {
			throw new ApiException(AppHttpStatus.INVALID_TOKEN);
		}

		Member member = memberRepository.getByEmail(clientEmail);

		SingKUserDetails userDetails = SingKUserDetails.of(member);
		TokenDto token = jwtRepository.generateTokenDto(userDetails.getId(), userDetails.getEmail(), userDetails.getRole());

        response.addCookie(createCookie(AUTHORIZATION_HEADER, BEARER_PREFIX + token.getAccessToken()));
        response.addCookie(createCookie(REFRESH_HEADER, token.getRefreshToken()));

		redisRepository.deleteValue(clientEmail);
		redisRepository.setValue(
			clientEmail,
			token.getRefreshToken(),
			jwtProperties.getRefreshExpirationMillis()
		);
	}

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(jwtProperties.getCookie().getMaxAge());
        cookie.setPath(jwtProperties.getCookie().getPath());
        cookie.setHttpOnly(jwtProperties.getCookie().isHttpOnly());
        cookie.setSecure(jwtProperties.getCookie().isSecure());
        return cookie;
    }

	@Override
	public void logout(TokenDto dto) {
		Claims claims = jwtRepository.parseToken(dto.getRefreshToken());

		// login 성공 시 저장한 email 조회
		String email = claims.getSubject();
		String savedRefreshToken = redisRepository.getValue(email);

		if (savedRefreshToken == null) {
			throw new ApiException(AppHttpStatus.UNAUTHORIZED);
		}

		redisRepository.deleteValue(email);
		// Access Token 블랙 리스트 처리
		redisRepository.setValue(
			dto.getAccessToken().substring(7),
			"logout",
			jwtProperties.getAccessExpirationMillis()
		);
	}

	@Override
	public PkResponseDto signup(MemberCreate memberCreate) {
        MemberStatistics statistics = MemberStatistics.empty();
		Member member = Member.from(memberCreate, statistics, passwordEncoderHolder);

		if (memberRepository.existsByEmail(member.getEmail())) {
			throw new ApiException(AppHttpStatus.DUPLICATE_MEMBER);
		}

		return PkResponseDto.of(memberRepository.save(member).getId());
	}

	@Override
	public void confirmNickname(String nickname) {
		if (memberRepository.existsByNickname(nickname)) {
			throw new ApiException(AppHttpStatus.DUPLICATE_NICKNAME);
		}
	}

	@Override
	public void sendCertificationCode(String email) {
		// 이메일 중복 체크
		if (memberRepository.existsByEmail(email)) {
			throw new ApiException(AppHttpStatus.DUPLICATE_MEMBER);
		}

		String code = codeGenerator.createCertification();
		mailService.sendCertificationCode(email, code);

		// Redis에 인증번호 등록
		redisRepository.setValue(AUTH_PREFIX + email, code, mailProperties.getExpirationMillis());
	}

	@Override
	public void confirmCertificationCode(MemberCertification memberCertification) {
		String serverCertificationCode = redisRepository.getValue(AUTH_PREFIX + memberCertification.getEmail());

		// 인증 코드가 만료되거나 일치하지 않는 경우
		if (serverCertificationCode == null || !serverCertificationCode.equals(memberCertification.getCode())) {
			throw new ApiException(AppHttpStatus.FAILED_VERIFY_CERTIFICATION_CODE);
		}
	}
}
