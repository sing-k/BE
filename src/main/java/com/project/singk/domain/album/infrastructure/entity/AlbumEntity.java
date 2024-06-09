package com.project.singk.domain.album.infrastructure.entity;

import java.time.LocalDateTime;

import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.album.domain.AlbumType;
import com.project.singk.global.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "ALBUMS")
@Getter
@NoArgsConstructor
public class AlbumEntity extends BaseTimeEntity {

	@Id
	@Column(updatable = false, length = 22)
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

    @Builder
    public AlbumEntity(String id, String name, AlbumType type, LocalDateTime releasedAt, long totalReviewer, long totalScore) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.releasedAt = releasedAt;
        this.totalReviewer = totalReviewer;
        this.totalScore = totalScore;
    }

	public static AlbumEntity from (Album album) {
		return AlbumEntity.builder()
			.id(album.getId())
			.name(album.getName())
			.type(album.getType())
			.releasedAt(album.getReleasedAt())
            .totalReviewer(album.getTotalReviewer())
            .totalScore(album.getTotalScore())
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
			.build();
	}
}
