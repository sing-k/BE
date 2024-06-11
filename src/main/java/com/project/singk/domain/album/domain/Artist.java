package com.project.singk.domain.album.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Artist {
	private final String id;
	private final String name;

	@Builder
    public Artist(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
