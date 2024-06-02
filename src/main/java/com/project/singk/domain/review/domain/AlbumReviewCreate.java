package com.project.singk.domain.review.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.singk.domain.album.infrastructure.entity.AlbumEntity;
import com.project.singk.domain.member.infrastructure.MemberEntity;
import com.project.singk.domain.review.infrastructure.AlbumReviewEntity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

@Getter
@ToString
public class AlbumReviewCreate {

	@NotBlank(message = "내용을 최소 1자 이상 입력해야 합니다.")
	@Size(max = 50, message = "내용을 최대 500자 까지 입력할 수 있습니다.")
	private String content;

	@NotNull
    @Range(min = 1, max = 5, message = "올바르지 않은 점수 형식입니다. e.g. 1 - 5")
	private int score;

    @Builder
    public AlbumReviewCreate(
            @JsonProperty("content") String content,
            @JsonProperty("score") int score
    ) {
        this.content = content;
        this.score = score;
    }
}
