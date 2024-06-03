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
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		mailSender.setHost(properties.getHost());
		mailSender.setUsername(properties.getUsername());
		mailSender.setPassword(properties.getPassword());
		mailSender.setPort(properties.getPort());

		mailSender.setJavaMailProperties(getMailProperties());

		return mailSender;
	}

	private Properties getMailProperties() {
		Properties properties = new Properties();
		properties.setProperty("mail.transport.protocol", "smtp"); // 프로토콜
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.debug", "true");
		properties.setProperty("mail.smtp.starttls.enable", "true");
		return properties;
	}
}
