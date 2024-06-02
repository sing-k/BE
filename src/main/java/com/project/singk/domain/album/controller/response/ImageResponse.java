package com.project.singk.domain.album.controller.response;

import com.project.singk.domain.album.domain.AlbumImage;

import lombok.Builder;
import lombok.Data;
import se.michaelthelin.spotify.model_objects.specification.Image;

@Data
@Builder
public class ImageResponse {
	private String imageUrl;
	private int width;
	private int height;

	public static ImageResponse from (AlbumImage image) {
		return ImageResponse.builder()
			.imageUrl(image.getImageUrl())
			.width(image.getWidth())
			.height(image.getHeight())
			.build();
	}
}
