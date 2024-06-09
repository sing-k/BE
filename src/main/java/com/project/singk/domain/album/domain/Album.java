package com.project.singk.domain.album.domain;

import java.time.LocalDateTime;

import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.review.domain.AlbumReviewCreate;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Album {
	private final String id;
	private final String name;
	private final AlbumType type;
	private final LocalDateTime releasedAt;
    private final long totalReviewer;
    private final long totalScore;

	@Builder
    public Album(String id, String name, AlbumType type, LocalDateTime releasedAt, long totalReviewer, long totalScore) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.releasedAt = releasedAt;
        this.totalReviewer = totalReviewer;
        this.totalScore = totalScore;
    }

    public Album increaseReviewScore(int score) {
        return Album.builder()
                .id(this.id)
                .name(this.name)
                .type(this.type)
                .releasedAt(this.releasedAt)
                .totalReviewer(this.totalReviewer + 1)
                .totalScore(this.totalScore + score)
                .build();
    }

    public Album decreaseReviewScore(int score) {

        return Album.builder()
                .id(this.id)
                .name(this.name)
                .type(this.type)
                .releasedAt(this.releasedAt)
                .totalReviewer(this.totalReviewer - 1)
                .totalScore(this.totalScore - score)
                .build();
    }

    public double calculateAverage() {
        return (double) this.totalScore / this.totalReviewer;
    }
}
