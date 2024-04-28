package com.project.singk.domain.mail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MailService {

	@Autowired
	@Qualifier("NaverMailSender")
	private JavaMailSender naverMailSender;

	@Autowired
	@Qualifier("GoogleMailSender")
	private JavaMailSender googleMailSender;

	private SimpleMailMessage createCertificationMessage(String to, String from, String code) {
		SimpleMailMessage message = new SimpleMailMessage();
		StringBuilder text = new StringBuilder();

		text.append("SingK ID: ").append(to).append("\n")
			.append("인증번호: ").append(code).append("\n");

		message.setTo(to);
		message.setFrom(from);
		message.setSubject(String.format("[SingK] 회원 가입 인증 메일 : %s", code));
		message.setText(text.toString());

		return message;
	}

	public String sendNaverCertificationMail(String to, String from) {

		String code = "123456";

		try {
			naverMailSender.send(createCertificationMessage(to, from, code));
		} catch (MailException e) {
			throw new ApiException(AppHttpStatus.FAILED_SEND_MAIL);
		}

		return code;
	}

	public String sendGoogleCertificationMail(String to, String from) {

		String code = "123456";

		try {
			googleMailSender.send(createCertificationMessage(to, from, code));
		} catch (MailException e) {
			throw new ApiException(AppHttpStatus.FAILED_SEND_MAIL);
		}

		return code;
	}


}
