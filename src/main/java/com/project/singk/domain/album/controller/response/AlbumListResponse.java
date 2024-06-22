package com.project.singk.domain.album.controller.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.singk.domain.album.domain.Album;

import com.project.singk.domain.album.domain.AlbumArtist;
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
    private long count;
    private double averageScore;
	private List<ArtistResponse> artists;
	private List<ImageResponse> images;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;

	public static AlbumListResponse from (Album album) {

		return AlbumListResponse.builder()
			.id(album.getId())
			.name(album.getName())
			.releasedAt(album.getReleasedAt())
			.artists(album.getArtists().stream()
                .map(AlbumArtist::getArtist)
				.map(ArtistResponse::from)
				.toList())
			.images(album.getImages().stream()
				.map(ImageResponse::from)
				.toList())
            .count(album.getStatistics().getTotalReviewer())
            .averageScore(album.getStatistics().getAverageScore())
            .modifiedAt(album.getStatistics().getModifiedAt())
			.build();
	}
}
