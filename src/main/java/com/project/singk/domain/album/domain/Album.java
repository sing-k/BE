package com.project.singk.domain.album.domain;

import java.time.LocalDateTime;
import java.util.List;

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
    private final List<Track> tracks;
    private final List<Artist> artists;
    private final List<AlbumImage> images;
    private final LocalDateTime modifiedAt;
    @Builder
    public Album(String id, String name, AlbumType type, LocalDateTime releasedAt, long totalReviewer, long totalScore, List<Track> tracks, List<Artist> artists, List<AlbumImage> images, LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.releasedAt = releasedAt;
        this.totalReviewer = totalReviewer;
        this.totalScore = totalScore;
        this.tracks = tracks;
        this.artists = artists;
        this.images = images;
        this.modifiedAt = modifiedAt;
    }

    public Album increaseReviewScore(int score) {
        return Album.builder()
                .id(this.id)
                .name(this.name)
                .type(this.type)
                .releasedAt(this.releasedAt)
                .totalReviewer(this.totalReviewer + 1)
                .totalScore(this.totalScore + score)
                .tracks(this.tracks)
                .artists(this.artists)
                .images(this.images)
                .modifiedAt(this.modifiedAt)
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
                .tracks(this.tracks)
                .artists(this.artists)
                .images(this.images)
                .modifiedAt(this.modifiedAt)
                .build();
    }

    public double calculateAverage() {
        if (this.totalReviewer == 0) return 0.0;

        return Math.round((double) this.totalScore / this.totalReviewer * 100) / 100.0;
    }
}
