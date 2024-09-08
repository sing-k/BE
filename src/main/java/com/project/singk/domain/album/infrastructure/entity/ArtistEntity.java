package com.project.singk.domain.album.infrastructure.entity;

import com.project.singk.domain.album.domain.Artist;
import com.project.singk.global.domain.BaseTimeEntity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;

@Entity
@Table(name = "ARTISTS")
@Getter
@NoArgsConstructor
public class ArtistEntity extends BaseTimeEntity implements Persistable<String> {

	@Id
	@Column(updatable = false, length = 22)
	private String id;

	@Column(name = "name")
	private String name;

    @Transient
    private LocalDateTime isNew;

    @Builder
    public ArtistEntity(String id, String name, LocalDateTime isNew) {
        this.id = id;
        this.name = name;
        this.isNew = isNew;
    }

	public static ArtistEntity from(Artist artist) {
		return ArtistEntity.builder()
			.id(artist.getId())
			.name(artist.getName())
            .isNew(artist.getCreatedAt())
			.build();
	}

	public Artist toModel() {
		return Artist.builder()
			.id(this.id)
			.name(this.name)
            .createdAt(this.getCreatedAt())
			.build();
	}

    @Override
    public boolean isNew() {
        return this.getCreatedAt() == null && this.isNew == null;
    }
}
