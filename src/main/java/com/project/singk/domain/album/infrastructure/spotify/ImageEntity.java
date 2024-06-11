package com.project.singk.domain.album.infrastructure.spotify;

import com.project.singk.domain.album.domain.AlbumImage;

import lombok.Builder;
import lombok.Getter;
import se.michaelthelin.spotify.model_objects.specification.Image;

@Getter
public class ImageEntity {
	private String imageUrl;
	private int width;
	private int height;

	@Builder
	public ImageEntity(String imageUrl, int width, int height) {
		this.imageUrl = imageUrl;
		this.width = width;
		this.height = height;
	}

	public static ImageEntity from (Image image) {
		return ImageEntity.builder()
			.imageUrl(image.getUrl())
			.width(image.getWidth())
			.height(image.getHeight())
			.build();
	}

	public AlbumImage toModel() {
		return AlbumImage.builder()
			.imageUrl(this.imageUrl)
			.width(this.width)
			.height(this.height)
			.build();
	}
}
