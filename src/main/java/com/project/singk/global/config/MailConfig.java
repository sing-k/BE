package com.project.singk.global.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.project.singk.global.properties.MailProperties;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class MailConfig {

	private final MailProperties properties;

	@Bean
	@Qualifier("NaverMailSender")
	public JavaMailSender naverMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		mailSender.setHost(properties.getNaver().getHost());
		mailSender.setUsername(properties.getNaver().getUsername());
		mailSender.setPassword(properties.getNaver().getPassword());
		mailSender.setPort(properties.getNaver().getPort());

		mailSender.setJavaMailProperties(getMailProperties(true, false));

		return mailSender;
	}

	@Bean
	@Qualifier("GoogleMailSender")
	public JavaMailSender googleMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		mailSender.setHost(properties.getGoogle().getHost());
		mailSender.setUsername(properties.getGoogle().getUsername());
		mailSender.setPassword(properties.getGoogle().getPassword());
		mailSender.setPort(properties.getGoogle().getPort());

		mailSender.setJavaMailProperties(getMailProperties(false, true));

		return mailSender;
	}

	private Properties getMailProperties(boolean isSSL, boolean isTLS) {
		Properties properties = new Properties();
		properties.setProperty("mail.transport.protocol", "smtp"); // 프로토콜
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.debug", "true");
		if (isSSL) properties.setProperty("mail.smtp.ssl.enable","true");
		if (isTLS) properties.setProperty("mail.smtp.starttls.enable", "true");
		return properties;
	}
}
