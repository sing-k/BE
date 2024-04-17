package com.project.singk.domain.album.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlbumType {
	SINGLE("싱글"),
	EP("EP"),
	FULL("정규");

	private final String name;

	public static AlbumType of(String type) {
		return AlbumType.valueOf(type.substring(1, type.length() - 1));
	}
}
