package com.project.singk.domain.member.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.singk.global.validate.Password;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MemberLogin {
	@Email(message = "올바르지 않은 이메일 형식입니다.")
	private final String email;
	@Password(message = "올바르지 않은 비밀번호 형식입니다.")
	private final String password;

	@Builder
	public MemberLogin(
		@JsonProperty("email") String email,
		@JsonProperty("password") String password
	) {
		this.email = email;
		this.password = password;
	}
}
