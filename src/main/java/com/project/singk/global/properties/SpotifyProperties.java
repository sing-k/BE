package com.project.singk.global.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
@AllArgsConstructor
@ConfigurationProperties(prefix = "spotify")
@ConfigurationPropertiesBinding
public class SpotifyProperties {

	private final String clientName;
	private final String clientId;
	private final String clientSecret;
	private final String redirectUri;

}
