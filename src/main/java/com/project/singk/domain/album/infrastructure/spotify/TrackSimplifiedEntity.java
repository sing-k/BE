package com.project.singk.domain.album.infrastructure.spotify;

import com.project.singk.domain.album.domain.Track;
import lombok.Builder;
import lombok.Getter;
import se.michaelthelin.spotify.model_objects.specification.TrackSimplified;

@Getter
public class TrackSimplifiedEntity {
	private String id;
	private String name;
	private int trackNumber;
	private long duration;
	private boolean isPlayable;
	private String previewUrl;

	@Builder
	public TrackSimplifiedEntity(String id, String name, int trackNumber, long duration, boolean isPlayable,
                                 String previewUrl) {
		this.id = id;
		this.name = name;
		this.trackNumber = trackNumber;
		this.duration = duration;
		this.isPlayable = isPlayable;
		this.previewUrl = previewUrl;
	}

	public static TrackSimplifiedEntity from(TrackSimplified track) {
		return TrackSimplifiedEntity.builder()
			.id(track.getId())
			.name(track.getName())
			.trackNumber(track.getTrackNumber())
			.duration(track.getDurationMs())
			.isPlayable(track.getIsPlayable())
			.previewUrl(track.getPreviewUrl())
			.build();
	}

	public Track toModel() {
		return Track.builder()
			.id(this.id)
			.name(this.name)
			.trackNumber(this.trackNumber)
			.duration(this.duration)
			.isPlayable(this.isPlayable)
			.previewUrl(this.previewUrl)
			.build();
	}
}
