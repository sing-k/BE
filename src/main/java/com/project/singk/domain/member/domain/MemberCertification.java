package com.project.singk.domain.member.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MemberCertification {

	@Email(message = "올바르지 않은 이메일 형식입니다.")
	private final String email;
	@Size(min = 6, max = 6, message = "올바르지 않은 인증코드 형식입니다.")
	private final String code;

	@Builder
	public MemberCertification(String email, String code) {
		this.email = email;
		this.code = code;
	}
}
