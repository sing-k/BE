package com.project.singk.domain.album.controller.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.singk.domain.album.domain.Album;

import com.project.singk.domain.album.domain.AlbumImage;
import com.project.singk.domain.album.domain.Artist;
import com.project.singk.domain.album.domain.Track;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class AlbumDetailResponse {
	private String id;
	private String name;
    private String type;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime releasedAt;
	private int trackCount;
	private List<ArtistResponse> artists;
	private List<TrackResponse> tracks;
	private List<ImageResponse> images;

	public static AlbumDetailResponse from (Album album) {
		return AlbumDetailResponse.builder()
			.id(album.getId())
			.name(album.getName())
            .type(album.getType().getName())
			.releasedAt(album.getReleasedAt())
			.trackCount(album.getTracks().size())
			.tracks(album.getTracks().stream().map(TrackResponse::from).toList())
			.images(album.getImages().stream().map(ImageResponse::from).toList())
			.artists(album.getArtists().stream().map(ArtistResponse::from).toList())
			.build();
	}
}
