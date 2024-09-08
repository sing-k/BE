package com.project.singk.mock;

import com.project.singk.domain.member.service.port.MailSender;

public class FakeMailSender implements MailSender {
	public String toEmail;
	public String code;
	@Override
	public void send(String toEmail, String code) {
		this.toEmail = toEmail;
		this.code = code;
	}
}
