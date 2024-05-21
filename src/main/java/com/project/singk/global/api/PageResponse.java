package com.project.singk.global.api;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PageResponse<T> {

	private int offset;
	private int limit;
	private int total;
	private List<T> items;

	public static <T> PageResponse<T> of (int offset, int limit, int total, List<T> items) {
		return PageResponse.<T>builder()
			.offset(offset)
			.limit(limit)
			.total(total)
			.items(items)
			.build();
	}
}
