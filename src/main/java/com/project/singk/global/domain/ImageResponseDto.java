package com.project.singk.global.domain;

import com.project.singk.domain.album.domain.AlbumImage;

import lombok.Builder;
import lombok.Data;
import se.michaelthelin.spotify.model_objects.specification.Image;

@Data
@Builder
public class ImageResponseDto {
	private String imageUrl;
	private int width;
	private int height;

	public static ImageResponseDto of (Image image) {
		return ImageResponseDto.builder()
			.imageUrl(image.getUrl())
			.width(image.getWidth())
			.height(image.getHeight())
			.build();
	}

	public static ImageResponseDto of (AlbumImage image) {
		return ImageResponseDto.builder()
			.imageUrl(image.getImageUrl())
			.width(image.getWidth())
			.height(image.getHeight())
			.build();
	}
}
