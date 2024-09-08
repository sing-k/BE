package com.project.singk.global.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@ConfigurationProperties(prefix = "async")
@ConfigurationPropertiesBinding
public class AsyncProperties {
	private final int corePoolSize;
	private final int maxPoolSize;
	private final int queueCapacity;
    private final String threadNamePrefix;
}
