package com.project.singk.domain.album.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Track {
	private final String id;
	private final String name;
	private final int trackNumber;
	private final long duration;
	private final boolean isPlayable;
	private final String previewUrl;
    private final LocalDateTime createdAt;
    private final List<TrackArtist> artists;

	@Builder
    public Track(String id, String name, int trackNumber, long duration, boolean isPlayable, String previewUrl, LocalDateTime createdAt, List<TrackArtist> artists) {
        this.id = id;
        this.name = name;
        this.trackNumber = trackNumber;
        this.duration = duration;
        this.isPlayable = isPlayable;
        this.previewUrl = previewUrl;
        this.createdAt = createdAt;
        this.artists = artists;
    }
}
