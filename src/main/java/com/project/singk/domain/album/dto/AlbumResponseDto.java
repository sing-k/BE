package com.project.singk.domain.album.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import com.project.singk.global.domain.ImageResponseDto;

import lombok.Builder;
import lombok.Data;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;

public class AlbumResponseDto {

	@Data
	@Builder
	public static class Simple {
		private String id;
		private String name;
		private LocalDateTime releasedAt;
		private LocalDateTime modifiedAt;
		private List<ArtistResponseDto.Simple> artists;
		private List<ImageResponseDto> images;

		public static AlbumResponseDto.Simple of (AlbumSimplified album) {

			List<ImageResponseDto> images = Arrays.stream(album.getImages())
				.map(ImageResponseDto::of)
				.toList();

			List<ArtistResponseDto.Simple> artists = Arrays.stream(album.getArtists())
				.map(ArtistResponseDto.Simple::of)
				.toList();

			return Simple.builder()
				.id(album.getId())
				.name(album.getName())
				.releasedAt(LocalDate.parse(
					album.getReleaseDate(),
					DateTimeFormatter.ofPattern("yyyy-MM-dd")
				).atStartOfDay())
				.artists(artists)
				.images(images)
				.build();
		}
	}


}
