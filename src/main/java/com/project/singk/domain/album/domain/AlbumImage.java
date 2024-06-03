package com.project.singk.domain.album.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AlbumImage {
	private final Long id;
	private final String imageUrl;
	private final int width;
	private final int height;
	private final String albumId;

	@Builder
    public AlbumImage(Long id, String imageUrl, int width, int height, String albumId) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.width = width;
        this.height = height;
        this.albumId = albumId;
    }
}
