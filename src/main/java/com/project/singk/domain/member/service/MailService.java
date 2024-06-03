package com.project.singk.domain.member.service;

import org.springframework.stereotype.Service;
import com.project.singk.domain.member.service.port.MailSender;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Service
@Builder
@RequiredArgsConstructor
public class MailService {
	private final MailSender mailSender;
	public void sendCertificationCode(String toEmail, String code) {
		mailSender.send(toEmail, code);
	}
}
