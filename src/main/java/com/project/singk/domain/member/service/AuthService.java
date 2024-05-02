package com.project.singk.domain.member.service;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.project.singk.domain.mail.domain.MailDomain;
import com.project.singk.domain.mail.service.MailService;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.dto.AuthCodeRequestDto;
import com.project.singk.domain.member.repository.MemberRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import com.project.singk.global.config.properties.MailProperties;
import com.project.singk.global.util.RedisUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

	private final String AUTH_PREFIX = "AuthCode";
	private final MemberRepository memberRepository;
	private final MailService mailService;
	private final RedisUtil redisUtil;
	private final MailProperties mailProperties;

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
