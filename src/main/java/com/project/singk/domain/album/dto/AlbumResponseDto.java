package com.project.singk.domain.album.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import com.project.singk.global.domain.ImageResponseDto;

import lombok.Builder;
import lombok.Data;
import se.michaelthelin.spotify.model_objects.specification.Album;
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

	@Data
	@Builder
	public static class Detail {

		private String id;
		private String name;
		private LocalDateTime releasedAt;
		private LocalDateTime modifiedAt;
		private int trackCount;
		private List<ArtistResponseDto.Simple> artists;
		private List<TrackResponseDto.Simple> tracks;
		private List<ImageResponseDto> images;

		public static AlbumResponseDto.Detail of (com.project.singk.domain.album.domain.Album album) {

			List<ImageResponseDto> images = album.getImages().stream()
				.map(ImageResponseDto::of)
				.toList();

			List<ArtistResponseDto.Simple> artists = album.getArtists().stream()
				.map(ArtistResponseDto.Simple::of)
				.toList();

			List<TrackResponseDto.Simple> tracks = album.getTracks().stream()
				.map(TrackResponseDto.Simple::of)
				.toList();

			return Detail.builder()
				.id(album.getId())
				.name(album.getName())
				.releasedAt(album.getReleasedAt())
				.artists(artists)
				.images(images)
				.tracks(tracks)
				.trackCount(tracks.size())
				.build();
		}
	}
}
