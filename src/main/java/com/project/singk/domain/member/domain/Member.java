package com.project.singk.domain.member.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.project.singk.domain.member.service.port.PasswordEncoderHolder;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Member {
	private Long id;
	private String email;
	private String password;
	private String imageUrl;
	private String nickname;
	private Gender gender;
	private String name;
	private LocalDateTime birthday;
	private Role role;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;

	@Builder
	public Member(Long id, String email, String password, String imageUrl, String nickname, Gender gender, String name,
		LocalDateTime birthday, Role role, LocalDateTime createdAt, LocalDateTime modifiedAt) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.imageUrl = imageUrl;
		this.nickname = nickname;
		this.gender = gender;
		this.name = name;
		this.birthday = birthday;
		this.role = role;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
	}

	public static Member from(
		MemberCreate memberCreate,
		PasswordEncoderHolder passwordEncoderHolder
	) {
		return Member.builder()
			.email(memberCreate.getEmail())
			.password(passwordEncoderHolder.encode(memberCreate.getPassword()))
			.nickname(memberCreate.getNickname())
			.gender(Gender.valueOf(memberCreate.getGender()))
			.name(memberCreate.getName())
			.birthday(LocalDate.parse(memberCreate.getBirthday(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay())
			.role(Role.ROLE_USER)
			.build();

	}
	public Member update(MemberUpdate memberUpdate) {
		return Member.builder()
			.id(this.id)
			.email(this.email)
			.password(this.password)
			.imageUrl(this.imageUrl)
			.nickname(memberUpdate.getNickname())
			.gender(this.gender)
			.name(this.name)
			.birthday(this.birthday)
			.role(this.role)
			.createdAt(this.createdAt)
			.modifiedAt(this.modifiedAt)
			.build();
	}

	public Member uploadProfileImage(String imageUrl) {
		return Member.builder()
			.id(this.id)
			.email(this.email)
			.password(this.password)
			.imageUrl(imageUrl)
			.nickname(this.nickname)
			.gender(this.gender)
			.name(this.name)
			.birthday(this.birthday)
			.role(this.role)
			.createdAt(this.createdAt)
			.modifiedAt(this.modifiedAt)
			.build();
	}

}
