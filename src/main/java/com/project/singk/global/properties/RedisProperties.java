package com.project.singk.global.properties;

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
    private final Cache cache;
    @Getter @ToString
    @AllArgsConstructor
    public static class Cache {
        private final long ttl;
    }

}
