package com.project.singk.domain.album.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Artist {
	private final String id;
	private final String name;
    private final LocalDateTime createdAt;

	@Builder
    public Artist(String id, String name, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }
}
