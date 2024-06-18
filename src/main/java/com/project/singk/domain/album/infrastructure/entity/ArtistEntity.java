package com.project.singk.domain.album.infrastructure.entity;

import com.project.singk.domain.album.domain.Artist;
import com.project.singk.global.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

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

    @Builder
    public ArtistEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

	public static ArtistEntity from(Artist artist) {
		return ArtistEntity.builder()
			.id(artist.getId())
			.name(artist.getName())
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
        return this.getCreatedAt() == null;
    }
}
