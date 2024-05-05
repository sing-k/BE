package com.project.singk.global.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
@AllArgsConstructor
@ConfigurationProperties(prefix = "jwt")
@ConfigurationPropertiesBinding
public class JwtProperties {

	private final String secretKey;
	private final long accessExpirationMillis;
	private final long refreshExpirationMillis;

}
