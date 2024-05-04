package com.project.singk.domain.member.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.singk.domain.member.domain.Gender;
import com.project.singk.domain.member.domain.Member;

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
public class SignupRequestDto {

	@Email
	private String email;

	// TODO : 유효성 검사
	@Size(min = 8)
	private String password;

	@Size(max = 12)
	private String nickname;

	private String birthday;

	private String gender;

	private String name;

	public Member toEntity(PasswordEncoder passwordEncoder) {
		return Member.builder()
			.email(this.email)
			.password(passwordEncoder.encode(this.password))
			.nickname(this.nickname)
			.birthday(LocalDate.parse(this.birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay())
			.gender(Gender.valueOf(this.gender))
			.name(this.name)
			.build();
	}
}
