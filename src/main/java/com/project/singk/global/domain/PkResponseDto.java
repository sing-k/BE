package com.project.singk.global.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PkResponseDto {
	private Long id;

	public static PkResponseDto of (Long id) {
		return PkResponseDto.builder()
			.id(id)
			.build();
	}
}
