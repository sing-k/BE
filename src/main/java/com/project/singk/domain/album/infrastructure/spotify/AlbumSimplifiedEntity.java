package com.project.singk.domain.album.infrastructure.spotify;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import com.project.singk.domain.album.domain.Album;

import lombok.Builder;
import lombok.Getter;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;

@Getter
public class AlbumSimplifiedEntity {
	private final String id;
	private final String name;
	private final LocalDateTime releasedAt;
	private final List<ArtistSimplifiedEntity> artists;
	private final List<ImageEntity> images;

	@Builder
	public AlbumSimplifiedEntity(String id, String name, LocalDateTime releasedAt,
		List<ArtistSimplifiedEntity> artists, List<ImageEntity> images) {
		this.id = id;
		this.name = name;
		this.releasedAt = releasedAt;
		this.artists = artists;
		this.images = images;
	}

	public static AlbumSimplifiedEntity from(AlbumSimplified album) {
		return AlbumSimplifiedEntity.builder()
			.id(album.getId())
			.name(album.getName())
			.releasedAt(LocalDate.parse(
				album.getReleaseDate(),
				DateTimeFormatter.ofPattern("yyyy-MM-dd")
			).atStartOfDay())
			.artists(Arrays.stream(album.getArtists())
				.map(ArtistSimplifiedEntity::from)
				.toList())
			.images(Arrays.stream(album.getImages())
				.map(ImageEntity::from)
				.toList())
			.build();
	}


	public Album toModel() {
		return Album.builder()
			.id(this.id)
			.name(this.name)
			.releasedAt(this.releasedAt)
            .totalReviewer(0)
            .totalScore(0)
            .artists(this.artists.stream().map(ArtistSimplifiedEntity::toModel).toList())
            .images(this.images.stream().map(ImageEntity::toModel).toList())
			.build();
	}
}
