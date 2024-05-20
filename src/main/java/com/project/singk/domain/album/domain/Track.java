package com.project.singk.domain.album.domain;

import com.project.singk.global.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "TRACKS")
public class Track extends BaseTimeEntity {

	@Id
	@Column(updatable = false, length = 22)
	private String id;

	@Column(name = "name")
	private String name;

	private int trackNumber;

	private long duration;

	private boolean isPlayable;

	private String previewUrl;

	@ManyToOne
	@JoinColumn(name = "album_id")
	private Album album;

	public void addAlbum(Album album) {
		this.album = album;

		if (!album.getTracks().contains(this)) {
			album.getTracks().add(this);
		}
	}
}
