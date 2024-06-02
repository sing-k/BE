package com.project.singk.domain.album.infrastructure.spotify;

import com.project.singk.domain.album.domain.Artist;

import lombok.Builder;
import lombok.Getter;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;

@Getter
public class ArtistSimplifiedEntity {
	private String id;
	private String name;

	@Builder
	public ArtistSimplifiedEntity(String id, String name) {
		this.id = id;
		this.name = name;
	}
	public static ArtistSimplifiedEntity from(ArtistSimplified artist) {
		return ArtistSimplifiedEntity.builder()
			.id(artist.getId())
			.name(artist.getName())
			.build();
	}

	public Artist toModel(String albumId) {
		return Artist.builder()
			.id(this.id)
			.name(this.name)
            .albumId(albumId)
			.build();
	}
}
