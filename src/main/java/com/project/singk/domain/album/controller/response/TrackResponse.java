package com.project.singk.domain.album.controller.response;

import com.project.singk.domain.album.domain.Track;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import se.michaelthelin.spotify.model_objects.specification.TrackSimplified;

@Getter
@Builder
@ToString
public class TrackResponse {
	private String id;
	private String name;
	private int trackNumber;
	private long duration;
	private boolean isPlayable;
	private String previewUrl;

	public static TrackResponse from (Track track) {
		return TrackResponse.builder()
			.id(track.getId())
			.name(track.getName())
			.trackNumber(track.getTrackNumber())
			.duration(track.getDuration())
			.isPlayable(track.isPlayable())
			.previewUrl(track.getPreviewUrl())
			.build();
	}

}
