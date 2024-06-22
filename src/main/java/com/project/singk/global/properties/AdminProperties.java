package com.project.singk.global.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;

@Getter @ToString
@AllArgsConstructor
@ConfigurationProperties(prefix = "admin")
@ConfigurationPropertiesBinding
public class AdminProperties {

	private final String email;
	private final String password;

}
