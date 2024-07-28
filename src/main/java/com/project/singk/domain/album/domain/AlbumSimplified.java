package com.project.singk.domain.album.domain;

import com.project.singk.domain.review.domain.AlbumReviewStatistics;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class AlbumSimplified {
	private final String id;
	private final String name;
	private final AlbumType type;
	private final LocalDateTime releasedAt;
    private final List<AlbumArtist> artists;
    private final List<AlbumImage> images;
    private final AlbumReviewStatistics statistics;
    private final LocalDateTime createdAt;
    @Builder
    public AlbumSimplified(String id, String name, AlbumType type, LocalDateTime releasedAt, List<AlbumArtist> artists, List<AlbumImage> images, AlbumReviewStatistics statistics, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.releasedAt = releasedAt;
        this.artists = artists;
        this.images = images;
        this.statistics = statistics;
        this.createdAt = createdAt;
    }
}
