package com.project.singk.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
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
public class AuthCodeRequestDto {

	@Email
	private String email;

	@Size(min = 6, max = 6)
	private String code;
}
