package com.project.singk.domain.album.controller.response;

import com.project.singk.domain.album.domain.AlbumImage;

import lombok.*;
import se.michaelthelin.spotify.model_objects.specification.Image;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
