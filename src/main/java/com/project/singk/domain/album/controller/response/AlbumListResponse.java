package com.project.singk.domain.album.controller.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.singk.domain.album.domain.Album;

import com.project.singk.domain.album.domain.AlbumArtist;
import com.project.singk.domain.album.domain.AlbumSimplified;
import com.project.singk.domain.review.controller.response.AlbumReviewStatisticsResponse;
import com.project.singk.domain.review.controller.response.AlbumReviewStatisticsSimpleResponse;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AlbumListResponse {
	private String id;
	private String name;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime releasedAt;
    private AlbumReviewStatisticsSimpleResponse statistics;
	private List<ArtistResponse> artists;
	private List<ImageResponse> images;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;

	public static AlbumListResponse from (AlbumSimplified album) {

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
            .statistics(AlbumReviewStatisticsSimpleResponse.from(album.getStatistics()))
            .modifiedAt(album.getStatistics().getModifiedAt())
			.build();
	}
}
