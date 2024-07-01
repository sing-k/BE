package com.project.singk.domain.album.controller.response;

import com.project.singk.domain.album.domain.Artist;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArtistResponse {
	private String id;
	private String name;
	public static ArtistResponse from (Artist artist) {
		return ArtistResponse.builder()
			.id(artist.getId())
			.name(artist.getName())
			.build();
	}
}
