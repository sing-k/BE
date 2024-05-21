package com.project.singk.domain.album.domain;

import com.project.singk.global.domain.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ALBUM_IMAGES")
public class AlbumImage extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String imageUrl;

	private int width;

	private int height;

	@ManyToOne
	@JoinColumn(name = "album_id")
	private Album album;

	public void addAlbum(Album album) {
		this.album = album;

		if (!album.getImages().contains(this)) {
			album.getImages().add(this);
		}
	}
}
