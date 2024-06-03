package com.project.singk.global.api;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Page<T> {

	private final int offset;
	private final int limit;
	private final int total;
	private final List<T> items;

	@Builder
	public Page(int offset, int limit, int total, List<T> items) {
		this.offset = offset;
		this.limit = limit;
		this.total = total;
		this.items = items;
	}

	public static <T> Page<T> of (int offset, int limit, int total, List<T> items) {
		return Page.<T>builder()
			.offset(offset)
			.limit(limit)
			.total(total)
			.items(items)
			.build();
	}
}
