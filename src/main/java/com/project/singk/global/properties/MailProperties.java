package com.project.singk.global.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
@AllArgsConstructor
@ConfigurationProperties(prefix = "mail")
@ConfigurationPropertiesBinding
public class MailProperties {

	private final String host;
	private final String username;
	private final String password;
	private final int port;
	private final long expirationMillis;
}
