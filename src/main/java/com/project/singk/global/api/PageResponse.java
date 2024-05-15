package com.project.singk.global.api;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import se.michaelthelin.spotify.model_objects.specification.Paging;

public class PageResponse {
	@Data
	@Builder
	public static class Spotify<T> {
		int offset;
		int limit;
		int total;
		List<T> items;

		public static <T> Spotify<T> of(int offset, int limit, int total, List<T> items) {
			return Spotify.<T>builder()
				.offset(offset)
				.limit(limit)
				.total(total)
				.items(items)
				.build();
		}
	}
}
