package com.project.singk.global.properties;

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
    private final Cookie cookie;

    @Getter @ToString
    @AllArgsConstructor
    public static class Cookie {
        private final int maxAge;
        private final String path;
        private final boolean httpOnly;
        private final boolean secure;
    }
}
