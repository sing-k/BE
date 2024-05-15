package com.project.singk.domain.album.dto;

import lombok.Builder;
import lombok.Data;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;

public class ArtistResponseDto {

	@Data
	@Builder
	public static class Simple {
		private String id;
		private String name;

		public static Simple of (ArtistSimplified artist) {
			return Simple.builder()
				.id(artist.getId())
				.name(artist.getName())
				.build();
		}
	}
}
