package com.project.singk.global.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {
	@NotBlank (message = "빈 문자열은 허용되지 않습니다.")
	private String accessToken;
	@NotBlank (message = "빈 문자열은 허용되지 않습니다.")
	private String refreshToken;
}
