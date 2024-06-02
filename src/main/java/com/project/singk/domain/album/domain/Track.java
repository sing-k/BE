package com.project.singk.domain.album.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Track {
	private final String id;
	private final String name;
	private final int trackNumber;
	private final long duration;
	private final boolean isPlayable;
	private final String previewUrl;
	private final String albumId;

	@Builder
    public Track(String id, String name, int trackNumber, long duration, boolean isPlayable, String previewUrl, String albumId) {
        this.id = id;
        this.name = name;
        this.trackNumber = trackNumber;
        this.duration = duration;
        this.isPlayable = isPlayable;
        this.previewUrl = previewUrl;
        this.albumId = albumId;
    }
}
