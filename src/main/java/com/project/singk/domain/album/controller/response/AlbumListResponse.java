package com.project.singk.domain.album.controller.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.singk.domain.album.domain.Album;

import com.project.singk.domain.album.domain.AlbumImage;
import com.project.singk.domain.album.domain.Artist;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
@Getter
@Builder
@ToString
public class AlbumListResponse {

	private String id;
	private String name;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime releasedAt;
	private List<ArtistResponse> artists;
	private List<ImageResponse> images;

	public static AlbumListResponse from (Album album, List<Artist> artists, List<AlbumImage> images) {

		return AlbumListResponse.builder()
			.id(album.getId())
			.name(album.getName())
			.releasedAt(album.getReleasedAt())
			.artists(artists.stream()
				.map(ArtistResponse::from)
				.toList())
			.images(images.stream()
				.map(ImageResponse::from)
				.toList())
			.build();
	}
}
