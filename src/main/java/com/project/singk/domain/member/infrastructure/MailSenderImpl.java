package com.project.singk.domain.member.infrastructure;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.project.singk.domain.member.service.port.MailSender;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import com.project.singk.global.properties.MailProperties;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MailSenderImpl implements MailSender {

	private final JavaMailSender javaMailSender;
	private final MailProperties mailProperties;
	@Override
	public void send(String toEmail, String code) {
		try {
			javaMailSender.send(createCertificationMessage(toEmail, code));
		} catch (MailException e) {
			throw new ApiException(AppHttpStatus.FAILED_SEND_MAIL);
		}
	}

	private SimpleMailMessage createCertificationMessage(String toEmail, String code) {
		SimpleMailMessage message = new SimpleMailMessage();
		StringBuilder text = new StringBuilder();

		text.append("SingK ID: ").append(toEmail).append("\n")
			.append("인증번호: ").append(code).append("\n");

		message.setTo(toEmail);
		message.setFrom(mailProperties.getUsername());
		message.setSubject(String.format("[SingK] 회원 가입 인증 메일 : %s", code));
		message.setText(text.toString());

		return message;
	}

}
