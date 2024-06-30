package com.project.singk.domain.album.infrastructure.entity;

import com.project.singk.domain.album.domain.Track;
import com.project.singk.domain.album.domain.TrackArtist;
import com.project.singk.global.domain.BaseTimeEntity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import java.util.List;

@Entity
@Table(name = "TRACKS")
@Getter
@NoArgsConstructor
public class TrackEntity extends BaseTimeEntity implements Persistable<String> {

	@Id
	@Column(updatable = false, length = 22)
	private String id;

	@Column(name = "name")
	private String name;

	@Column(name = "track_number")
	private int trackNumber;

	@Column(name = "duration")
	private long duration;

	@Column(name = "is_playable")
	private boolean isPlayable;

	@Column(name = "preview_url")
	private String previewUrl;

    @JoinColumn(name = "album_id", updatable = false, nullable = false)
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<TrackArtistEntity> artists;

    @Builder
    public TrackEntity(String id, String name, int trackNumber, long duration, boolean isPlayable, String previewUrl, List<TrackArtistEntity> artists) {
        this.id = id;
        this.name = name;
        this.trackNumber = trackNumber;
        this.duration = duration;
        this.isPlayable = isPlayable;
        this.previewUrl = previewUrl;
        this.artists = artists;
    }

	public static TrackEntity from (Track track) {
		return TrackEntity.builder()
			.id(track.getId())
			.name(track.getName())
			.trackNumber(track.getTrackNumber())
			.duration(track.getDuration())
			.isPlayable(track.isPlayable())
			.previewUrl(track.getPreviewUrl())
            .artists(track.getArtists().stream()
                    .map(TrackArtistEntity::from)
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
            .createdAt(this.getCreatedAt())
            .artists(this.artists.stream().map(TrackArtistEntity::toModel).toList())
			.build();
	}

    @Override
    public boolean isNew() {
        return this.getCreatedAt() == null;
    }
}
