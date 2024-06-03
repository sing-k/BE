package com.project.singk.domain.member.service.port;

public interface MailSender {
	void send(String toEmail, String code);
}
