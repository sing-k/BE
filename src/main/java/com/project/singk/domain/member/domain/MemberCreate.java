package com.project.singk.domain.member.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.singk.global.validate.Date;
import com.project.singk.global.validate.Password;
import com.project.singk.global.validate.ValidEnum;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MemberCreate {

	@Email(message = "올바르지 않은 이메일 형식입니다.")
	private final String email;

	@Password(message = "올바르지 않은 비밀번호 형식입니다.")
	private final String password;

	@Size(min = 1, max = 12, message = "올바르지 않은 닉네임 형식입니다.")
	private final String nickname;

	@Date(message = "올바르지 않은 날짜 형식입니다. e.g. yyyy-MM-dd")
	private final String birthday;

	@ValidEnum(enumClass = Gender.class)
	private final String gender;

	@NotBlank(message = "올바르지 않은 이름 형식입니다.")
	private final String name;

	@Builder
	public MemberCreate(
		@JsonProperty("email") String email,
		@JsonProperty("password") String password,
		@JsonProperty("nickname") String nickname,
		@JsonProperty("birthday") String birthday,
		@JsonProperty("gender") String gender,
		@JsonProperty("name") String name
	) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.birthday = birthday;
		this.gender = gender;
		this.name = name;
	}
}
