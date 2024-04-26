package com.project.singk.global.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.project.singk.global.config.properties.MailProperties;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class MailConfig {

	private final MailProperties properties;

	@Bean
	public JavaMailSender naverMailService() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		mailSender.setHost("smtp.naver.com");
		mailSender.setUsername(properties.getNaver().getUsername());
		mailSender.setPassword(properties.getNaver().getPassword());
		mailSender.setPort(465);

		mailSender.setJavaMailProperties(getNaverMailProperties());

		return mailSender;
	}

	private Properties getNaverMailProperties() {
		Properties properties = new Properties();
		properties.setProperty("mail.transport.protocol", "smtp"); // 프로토콜
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.debug", "true");
		properties.setProperty("mail.smtp.ssl.trust","smtp.naver.com");
		properties.setProperty("mail.smtp.ssl.enable","true");
		return properties;
	}
}
