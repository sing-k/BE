package com.project.singk.domain.member.controller.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.singk.domain.member.domain.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class MyProfileResponse {
	private Long id;
	private String email;
	private String imageUrl;
	private String nickname;
	private String name;
	private String gender;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime birthday;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime createdAt;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime modifiedAt;
	public static MyProfileResponse from(Member member, String imageUrl) {
		return MyProfileResponse.builder()
			.id(member.getId())
			.email(member.getEmail())
			.imageUrl(imageUrl)
			.nickname(member.getNickname())
			.name(member.getName())
			.gender(member.getGender().getName())
			.birthday(member.getBirthday())
			.createdAt(member.getCreatedAt())
			.modifiedAt(member.getModifiedAt())
			.build();
	}
}
