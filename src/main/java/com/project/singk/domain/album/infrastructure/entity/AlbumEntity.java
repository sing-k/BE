package com.project.singk.domain.album.infrastructure.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.album.domain.AlbumType;
import com.project.singk.global.domain.BaseTimeEntity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "ALBUMS", indexes = {
        @Index(name = "idx_album_modified_at", columnList = "modifiedAt"),
        @Index(name = "idx_album_total_reviewer", columnList = "totalReviewer"),
        @Index(name = "idx_album_total_score", columnList = "totalScore")
})
@Getter
@NoArgsConstructor
public class AlbumEntity extends BaseTimeEntity {

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

    @ColumnDefault("0")
    @Column(name = "total_reviewer")
    private long totalReviewer;

    @ColumnDefault("0")
    @Column(name = "total_Score")
    private long totalScore;

    @JoinColumn(name = "album_id", updatable = false, nullable = false)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrackEntity> tracks;

    @JoinColumn(name = "album_id", updatable = false, nullable = false)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArtistEntity> artists;

    @JoinColumn(name = "album_id", updatable = false, nullable = false)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AlbumImageEntity> images;

    @Builder
    public AlbumEntity(String id, String name, AlbumType type, LocalDateTime releasedAt, long totalReviewer, long totalScore, List<TrackEntity> tracks, List<ArtistEntity> artists, List<AlbumImageEntity> images) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.releasedAt = releasedAt;
        this.totalReviewer = totalReviewer;
        this.totalScore = totalScore;
        this.tracks = tracks;
        this.artists = artists;
        this.images = images;
    }
	public static AlbumEntity from (Album album) {
		return AlbumEntity.builder()
			.id(album.getId())
			.name(album.getName())
			.type(album.getType())
			.releasedAt(album.getReleasedAt())
            .totalReviewer(album.getTotalReviewer())
            .totalScore(album.getTotalScore())
            .tracks(album.getTracks().stream().map(TrackEntity::from).toList())
            .artists(album.getArtists().stream().map(ArtistEntity::from).toList())
            .images(album.getImages().stream().map(AlbumImageEntity::from).toList())
			.build();
	}

	public Album toModel() {
		return Album.builder()
			.id(this.id)
			.name(this.name)
			.type(this.type)
			.releasedAt(this.releasedAt)
            .totalReviewer(this.totalReviewer)
            .totalScore(this.totalScore)
            .tracks(this.tracks.stream().map(TrackEntity::toModel).toList())
            .artists(this.artists.stream().map(ArtistEntity::toModel).toList())
            .images(this.images.stream().map(AlbumImageEntity::toModel).toList())
            .modifiedAt(this.getModifiedAt())
			.build();
	}
}
