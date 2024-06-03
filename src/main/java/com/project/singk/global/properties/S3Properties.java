package com.project.singk.global.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
@AllArgsConstructor
@ConfigurationProperties(prefix = "cloud.aws.s3")
@ConfigurationPropertiesBinding
public class S3Properties {

	private final String bucket;
	private final Credentials credentials;
	private final Stack stack;
	private final long expirationMillis;

	@Getter @ToString
	@AllArgsConstructor
	public static class Credentials {
		private final String accessKey;
		private final String secretKey;
	}
	@Getter @ToString
	@AllArgsConstructor
	public static class Stack {
		private final boolean auto;
	}
}
