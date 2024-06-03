package com.project.singk.domain.album.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Artist {
	private final String id;
	private final String name;
	private final String albumId;

	@Builder
    public Artist(String id, String name, String albumId) {
        this.id = id;
        this.name = name;
        this.albumId = albumId;
    }
}
