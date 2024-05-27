package com.project.singk.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.project.singk.global.properties.S3Properties;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
@RequiredArgsConstructor
public class S3Config {

	private final S3Properties s3Properties;

	@Bean
	public S3Client s3Client() {
		final AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(
			s3Properties.getCredentials().getAccessKey(),
			s3Properties.getCredentials().getSecretKey()
		);

		return S3Client.builder()
			.region(Region.AP_NORTHEAST_2)
			.credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
			.build();
	}

	@Bean
	public S3Presigner s3Presigner() {
		final AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(
			s3Properties.getCredentials().getAccessKey(),
			s3Properties.getCredentials().getSecretKey()
		);
		return S3Presigner.builder()
			.region(Region.AP_NORTHEAST_2)
			.credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
			.build();
	}
}
