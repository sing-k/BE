package com.project.singk.domain.album.infrastructure.entity;

import com.project.singk.domain.album.domain.AlbumImage;
import com.project.singk.global.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ALBUM_IMAGES")
@Getter
@NoArgsConstructor
public class AlbumImageEntity extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "image_url")
	private String imageUrl;

	@Column(name = "width")
	private int width;

	@Column(name = "height")
	private int height;

	@Column(name = "album_id", length = 22)
	private String albumId;

    @Builder
    public AlbumImageEntity(Long id, String imageUrl, int width, int height, String albumId) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.width = width;
        this.height = height;
        this.albumId = albumId;
    }

	public static AlbumImageEntity from (AlbumImage image) {
		return AlbumImageEntity.builder()
            .id(image.getId())
			.imageUrl(image.getImageUrl())
			.width(image.getWidth())
			.height(image.getHeight())
			.albumId(image.getAlbumId())
			.build();
	}

	public AlbumImage toModel() {
		return AlbumImage.builder()
            .id(this.id)
			.imageUrl(this.imageUrl)
			.width(this.width)
			.height(this.height)
			.albumId(this.albumId)
			.build();
	}
}
