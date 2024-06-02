package com.project.singk.domain.album.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Album {
	private final String id;
	private final String name;
	private final AlbumType type;
	private final LocalDateTime releasedAt;

	@Builder
    public Album(String id, String name, AlbumType type, LocalDateTime releasedAt) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.releasedAt = releasedAt;
    }
}
