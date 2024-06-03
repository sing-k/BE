package com.project.singk.domain.member.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.project.singk.mock.FakeMailSender;
import com.project.singk.mock.TestContainer;

class MailServiceTest {

	@Test
	void 인증코드와_본문이_제대로_전송되는지_확인한다() {
		// given
		FakeMailSender mailSender = new FakeMailSender();
		MailService mailService = MailService.builder()
			.mailSender(mailSender)
			.build();

		// when
		mailService.sendCertificationCode("singk@gmail.com", "123456");

		// then
		assertThat(mailSender.toEmail).isEqualTo("singk@gmail.com");
		assertThat(mailSender.code).isEqualTo("123456");
	}
}
