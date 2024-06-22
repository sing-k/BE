package com.project.singk.domain.album.infrastructure.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.album.domain.AlbumType;
import com.project.singk.domain.review.infrastructure.AlbumReviewStatisticsEntity;
import com.project.singk.global.domain.BaseTimeEntity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

@Entity
@Table(name = "ALBUMS")
@Getter
@NoArgsConstructor
public class AlbumEntity extends BaseTimeEntity implements Persistable<String> {

	@Id
	@Column(updatable = false)
	private String id;

	@Column(name = "name")
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private AlbumType type;

	@Column(name = "released_at")
	private LocalDateTime releasedAt;

    @JoinColumn(name = "album_id", updatable = false, nullable = false)
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<TrackEntity> tracks;

    @JoinColumn(name = "album_id", updatable = false, nullable = false)
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<AlbumImageEntity> images;

    @JoinColumn(name = "album_id", updatable = false, nullable = false)
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<AlbumArtistEntity> artists;

    @JoinColumn(name = "statistic_id")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private AlbumReviewStatisticsEntity statistics;

    @Transient
    private LocalDateTime isNew;
    @Builder
    public AlbumEntity(String id, String name, AlbumType type, LocalDateTime releasedAt, List<TrackEntity> tracks, List<AlbumImageEntity> images, List<AlbumArtistEntity> artists, AlbumReviewStatisticsEntity statistics, LocalDateTime isNew) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.releasedAt = releasedAt;
        this.tracks = tracks;
        this.images = images;
        this.artists = artists;
        this.statistics = statistics;
        this.isNew = isNew;
    }

	public static AlbumEntity from (Album album) {
		return AlbumEntity.builder()
			.id(album.getId())
			.name(album.getName())
			.type(album.getType())
			.releasedAt(album.getReleasedAt())
            .tracks(album.getTracks().stream().map(TrackEntity::from).toList())
            .images(album.getImages().stream().map(AlbumImageEntity::from).toList())
            .artists(album.getArtists().stream().map(AlbumArtistEntity::from).toList())
            .statistics(AlbumReviewStatisticsEntity.from(album.getStatistics()))
            .isNew(album.getCreatedAt())
			.build();
	}

	public Album toModel() {
		return Album.builder()
			.id(this.id)
			.name(this.name)
			.type(this.type)
			.releasedAt(this.releasedAt)
            .tracks(this.tracks.stream().map(TrackEntity::toModel).toList())
            .images(this.images.stream().map(AlbumImageEntity::toModel).toList())
            .artists(this.artists.stream().map(AlbumArtistEntity::toModel).toList())
            .statistics(this.statistics.toModel())
            .createdAt(this.getCreatedAt())
			.build();
	}

    @Override
    public boolean isNew() {
        return this.getCreatedAt() == null && this.isNew == null;
    }
}
