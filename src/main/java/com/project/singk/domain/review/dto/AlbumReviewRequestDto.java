package com.project.singk.domain.review.dto;

import com.project.singk.domain.album.infrastructure.entity.AlbumEntity;
import com.project.singk.domain.member.infrastructure.MemberEntity;
import com.project.singk.domain.review.domain.AlbumReview;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlbumReviewRequestDto {

	@NotNull
	@Size(max = 500)
	private String content;

	@NotNull
	private int score;

	public AlbumReview toEntity(MemberEntity member, AlbumEntity albumEntity) {
		return AlbumReview.builder()
			.content(content)
			.score(score)
			.member(member)
			.albumEntity(albumEntity)
			.build();
	}
}
