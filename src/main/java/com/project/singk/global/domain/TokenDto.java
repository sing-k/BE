package com.project.singk.global.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDto {
	private String accessToken;
	private String refreshToken;
}
