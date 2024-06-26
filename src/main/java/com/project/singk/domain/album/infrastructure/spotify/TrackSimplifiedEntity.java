package com.project.singk.domain.album.infrastructure.spotify;

import com.project.singk.domain.album.domain.Track;
import com.project.singk.domain.album.domain.TrackArtist;
import lombok.Builder;
import lombok.Getter;
import se.michaelthelin.spotify.model_objects.specification.TrackSimplified;

import java.util.Arrays;
import java.util.List;

@Getter
public class TrackSimplifiedEntity {
	private String id;
	private String name;
	private int trackNumber;
	private long duration;
	private boolean isPlayable;
	private String previewUrl;
    private List<ArtistSimplifiedEntity> artists;

	@Builder
	public TrackSimplifiedEntity(String id, String name, int trackNumber, long duration, boolean isPlayable,
                                 String previewUrl, List<ArtistSimplifiedEntity> artists) {
		this.id = id;
		this.name = name;
		this.trackNumber = trackNumber;
		this.duration = duration;
		this.isPlayable = isPlayable;
		this.previewUrl = previewUrl;
        this.artists = artists;
	}

	public static TrackSimplifiedEntity from(TrackSimplified track) {
		return TrackSimplifiedEntity.builder()
			.id(track.getId())
			.name(track.getName())
			.trackNumber(track.getTrackNumber())
			.duration(track.getDurationMs())
			.isPlayable(track.getIsPlayable())
			.previewUrl(track.getPreviewUrl())
            .artists(Arrays.stream(track.getArtists())
                    .map(ArtistSimplifiedEntity::from)
                    .toList())
			.build();
	}

	public Track toModel() {
		return Track.builder()
			.id(this.id)
			.name(this.name)
			.trackNumber(this.trackNumber)
			.duration(this.duration)
			.isPlayable(this.isPlayable)
			.previewUrl(this.previewUrl)
            .artists(this.artists.stream()
                    .map(ArtistSimplifiedEntity::toModel)
                    .map(TrackArtist::from)
                    .toList())
			.build();
	}
}
