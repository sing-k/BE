package com.project.singk.domain.album.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlbumType {
	SINGLE("[싱글]", "싱글"),
	EP("[EP]", "EP"),
	FULL("[정규]", "정규"),
	OST("[OST]", "OST");

	private final String match;
	private final String name;

	public static AlbumType of(String type) {
		for (AlbumType a : AlbumType.values()) {
			if (a.match.equals(type)) return a;
		}

		return null;
	}
}
