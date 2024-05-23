package com.project.singk.domain.review.dto;

import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.member.domain.Member;
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

	public AlbumReview toEntity(Member member, Album album) {
		return AlbumReview.builder()
			.content(content)
			.score(score)
			.member(member)
			.album(album)
			.build();
	}
}
