package com.project.singk.domain.album.domain;

import java.time.LocalDateTime;
import java.util.List;

import com.project.singk.domain.review.domain.AlbumReviewStatistics;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Album {
	private final String id;
	private final String name;
	private final AlbumType type;
	private final LocalDateTime releasedAt;
    private final List<Track> tracks;
    private final List<Artist> artists;
    private final List<AlbumImage> images;
    private final AlbumReviewStatistics statistics;
    @Builder
    public Album(String id, String name, AlbumType type, LocalDateTime releasedAt, List<Track> tracks, List<Artist> artists, List<AlbumImage> images, AlbumReviewStatistics statistics) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.releasedAt = releasedAt;
        this.tracks = tracks;
        this.artists = artists;
        this.images = images;
        this.statistics = statistics;
    }

    public Album updateStatistic(AlbumReviewStatistics statistics) {
        return Album.builder()
                .id(this.id)
                .name(this.name)
                .type(this.type)
                .releasedAt(this.releasedAt)
                .tracks(this.tracks)
                .artists(this.artists)
                .images(this.images)
                .statistics(statistics)
                .build();
    }
}
