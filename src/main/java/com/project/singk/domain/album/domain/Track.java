package com.project.singk.domain.album.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Track {
	private final String id;
	private final String name;
	private final int trackNumber;
	private final long duration;
	private final boolean isPlayable;
	private final String previewUrl;
    private LocalDateTime createdAt;

	@Builder
    public Track(String id, String name, int trackNumber, long duration, boolean isPlayable, String previewUrl, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.trackNumber = trackNumber;
        this.duration = duration;
        this.isPlayable = isPlayable;
        this.previewUrl = previewUrl;
        this.createdAt = createdAt;
    }
}
