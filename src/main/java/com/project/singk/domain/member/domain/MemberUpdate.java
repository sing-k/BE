package com.project.singk.domain.member.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MemberUpdate {

	@Size(min = 1, max = 12, message = "올바르지 않은 닉네임 형식입니다.")
	private final String nickname;

	@Builder
	@JsonCreator
	public MemberUpdate(
		@JsonProperty("nickname") String nickname
	) {
		this.nickname = nickname;
	}
}
