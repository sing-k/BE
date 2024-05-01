package com.project.singk.global.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
@AllArgsConstructor
@ConfigurationProperties(prefix = "spring.data.redis")
@ConfigurationPropertiesBinding
public class RedisProperties {

	private final String host;
	private final int port;

}
