package com.project.singk.domain.album.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.project.singk.domain.album.domain.Album;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlbumListResponseDto {
	private Long id;
	private String imageUrl;
	private String name;
	private String artist;
	private LocalDateTime releasedAt;
	private List<String> genres;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;

	public static AlbumListResponseDto of (Album album, List<String> genres) {
		return AlbumListResponseDto.builder()
			.id(album.getId())
			.imageUrl(album.getImageUrl())
			.name(album.getName())
			.artist(album.getArtist())
			.genres(genres)
			.releasedAt(album.getReleasedAt())
			.createdAt(album.getCreatedAt())
			.modifiedAt(album.getModifiedAt())
			.build();
	}
}
