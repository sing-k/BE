package com.project.singk.domain.album.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.singk.global.domain.BaseTimeEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "ALBUMS")
public class Album extends BaseTimeEntity {

	@Id
	@Column(updatable = false, length = 22)
	private String id;

	private String name;

	@Enumerated(EnumType.STRING)
	private AlbumType type;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime releasedAt;

	private int trackCount;

	@Builder.Default
	@OneToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "album")
	private List<Track> tracks = new ArrayList<>();

	@Builder.Default
	@OneToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "album")
	private List<AlbumImage> images = new ArrayList<>();

	@Builder.Default
	@OneToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "album")
	private List<Artist> artists = new ArrayList<>();

	public void addTracks(List<Track> tracks) {
		for (Track track : tracks) {
			this.tracks.add(track);

			if (track.getAlbum() != this) {
				track.addAlbum(this);
			}
		}
	}
	public void addAlbumImages(List<AlbumImage> images) {
		for (AlbumImage image : images) {
			this.images.add(image);

			if (image.getAlbum() != this) {
				image.addAlbum(this);
			}
		}
	}
	public void addArtists(List<Artist> artists) {
		for (Artist artist : artists) {
			this.artists.add(artist);

			if (artist.getAlbum() != this) {
				artist.addAlbum(this);
			}
		}
	}
}
