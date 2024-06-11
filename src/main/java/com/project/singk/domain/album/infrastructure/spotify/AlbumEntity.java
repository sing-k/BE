package com.project.singk.domain.album.infrastructure.spotify;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.album.domain.AlbumType;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AlbumEntity {
	private final String id;
	private final String name;
	private final String type;
	private final LocalDateTime releasedAt;
	private final List<TrackSimplifiedEntity> tracks;
	private final List<ArtistSimplifiedEntity> artists;
	private final List<ImageEntity> images;

	@Builder
	public AlbumEntity(String id, String name, String type, LocalDateTime releasedAt,
		List<TrackSimplifiedEntity> tracks,
		List<ArtistSimplifiedEntity> artists, List<ImageEntity> images) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.releasedAt = releasedAt;
		this.tracks = tracks;
		this.artists = artists;
		this.images = images;
	}

	public static AlbumEntity from(se.michaelthelin.spotify.model_objects.specification.Album album) {
		return AlbumEntity.builder()
			.id(album.getId())
			.name(album.getName())
			.type(album.getAlbumType().getType())
			.releasedAt(LocalDate.parse(
				album.getReleaseDate(),
				DateTimeFormatter.ofPattern("yyyy-MM-dd")
			).atStartOfDay())
			.tracks(Arrays.stream(album.getTracks().getItems())
				.map(TrackSimplifiedEntity::from)
				.toList())
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
                .type(AlbumType.of(this.type, this.tracks.size()))
                .releasedAt(this.releasedAt)
                .totalReviewer(0)
                .totalScore(0)
                .tracks(this.tracks.stream().map(TrackSimplifiedEntity::toModel).toList())
                .artists(this.artists.stream().map(ArtistSimplifiedEntity::toModel).toList())
                .images(this.images.stream().map(ImageEntity::toModel).toList())
                .build();
    }
}
