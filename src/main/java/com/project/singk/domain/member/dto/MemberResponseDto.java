package com.project.singk.domain.member.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.singk.domain.album.dto.ArtistResponseDto;
import com.project.singk.domain.album.dto.TrackResponseDto;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.global.domain.ImageResponseDto;

import lombok.Builder;
import lombok.Data;

public class MemberResponseDto {

	@Data
	@Builder
	public static class Detail {

		private Long id;
		private String email;
		private String imageUrl;
		private String nickname;
		private String name;
		private String gender;
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
		private LocalDateTime birthday;

		public static MemberResponseDto.Detail of (Member member, String imageUrl) {
			return Detail.builder()
				.id(member.getId())
				.email(member.getEmail())
				.imageUrl(imageUrl)
				.nickname(member.getNickname())
				.name(member.getName())
				.gender(member.getGender().getName())
				.birthday(member.getBirthday())
				.build();
		}
	}
}
