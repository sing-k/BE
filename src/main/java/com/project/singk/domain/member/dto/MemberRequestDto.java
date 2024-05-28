package com.project.singk.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberRequestDto {

	@NotBlank
	private String nickname;

	@JsonCreator
	public MemberRequestDto (@JsonProperty("nickname") String nickname) {
		this.nickname = nickname;
	}
}
