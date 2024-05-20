package com.project.singk.domain.album.dto;

import com.project.singk.domain.album.domain.Track;

import lombok.Builder;
import lombok.Data;
import se.michaelthelin.spotify.model_objects.specification.TrackSimplified;

public class TrackResponseDto {
	@Data
	@Builder
	public static class Simple {
		private String id;
		private String name;
		private int trackNumber;
		private long duration;
		private boolean isPlayable;
		private String previewUrl;

		public static TrackResponseDto.Simple of (TrackSimplified track) {
			return Simple.builder()
				.id(track.getId())
				.name(track.getName())
				.trackNumber(track.getTrackNumber())
				.duration(track.getDurationMs())
				.isPlayable(track.getIsPlayable())
				.previewUrl(track.getPreviewUrl())
				.build();
		}

		public static TrackResponseDto.Simple of (Track track) {
			return Simple.builder()
				.id(track.getId())
				.name(track.getName())
				.trackNumber(track.getTrackNumber())
				.duration(track.getDuration())
				.isPlayable(track.isPlayable())
				.previewUrl(track.getPreviewUrl())
				.build();
		}
	}
}
