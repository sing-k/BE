package com.project.singk.domain.member.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.singk.domain.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class MemberResponse {
	private Long id;
	private String imageUrl;
	private String nickname;
	private String gender;
    private MemberStatisticsResponse statistics;
	public static MemberResponse from(Member member, String imageUrl) {
		return MemberResponse.builder()
			.id(member.getId())
			.imageUrl(imageUrl)
			.nickname(member.getNickname())
			.gender(member.getGender().getName())
            .statistics(MemberStatisticsResponse.from(member.getStatistics()))
			.build();
	}
}
