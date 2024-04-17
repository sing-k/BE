package com.project.singk.domain.album.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.album.domain.AlbumType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter @ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlbumRequestDto {

	private Long melonId;
	private String name;
	private String type;
	private String artist;
	private String imageUrl;
	private String releasedAt;
	// 장르
	private String genre;
	// 수록곡
	private List<String> tracks;

	public Album toAlbumEntity() {
		return Album.builder()
			.melonId(this.melonId)
			.name(this.name)
			.type(AlbumType.of(this.type))
			.artist(this.artist)
			.releasedAt(LocalDateTime.parse(this.releasedAt, DateTimeFormatter.ofPattern("yyyy.MM.dd")))
			.build();
	}


}
