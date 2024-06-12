package com.project.singk.domain.album.infrastructure.entity;

import com.project.singk.domain.album.domain.Track;
import com.project.singk.global.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TRACKS")
@Getter
@NoArgsConstructor
public class TrackEntity extends BaseTimeEntity {

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

    @Builder
    public TrackEntity(String id, String name, int trackNumber, long duration, boolean isPlayable, String previewUrl) {
        this.id = id;
        this.name = name;
        this.trackNumber = trackNumber;
        this.duration = duration;
        this.isPlayable = isPlayable;
        this.previewUrl = previewUrl;
    }

	public static TrackEntity from (Track track) {
		return TrackEntity.builder()
			.id(track.getId())
			.name(track.getName())
			.trackNumber(track.getTrackNumber())
			.duration(track.getDuration())
			.isPlayable(track.isPlayable())
			.previewUrl(track.getPreviewUrl())
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
			.build();
	}
}
