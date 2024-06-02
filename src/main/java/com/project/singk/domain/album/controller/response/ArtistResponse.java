package com.project.singk.domain.album.controller.response;

import com.project.singk.domain.album.domain.Artist;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ArtistResponse {
	private final String id;
	private final String name;
	public static ArtistResponse from (Artist artist) {
		return ArtistResponse.builder()
			.id(artist.getId())
			.name(artist.getName())
			.build();
	}
}
