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

	@Builder
	public AlbumEntity(String id, String name, AlbumType type, LocalDateTime releasedAt) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.releasedAt = releasedAt;
	}

	public static AlbumEntity from (Album album) {
		return AlbumEntity.builder()
			.id(album.getId())
			.name(album.getName())
			.type(album.getType())
			.releasedAt(album.getReleasedAt())
			.build();
	}

	public Album toModel() {
		return Album.builder()
			.id(this.id)
			.name(this.name)
			.type(this.type)
			.releasedAt(this.releasedAt)
			.build();
	}
}
