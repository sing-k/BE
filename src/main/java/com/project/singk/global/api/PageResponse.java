package com.project.singk.global.api;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PageResponse<T> {

	private final int offset;
	private final int limit;
	private final int total;
	private final List<T> items;

	@Builder
	public PageResponse(int offset, int limit, int total, List<T> items) {
		this.offset = offset;
		this.limit = limit;
		this.total = total;
		this.items = items;
	}

	public static <T> PageResponse<T> of (int offset, int limit, int total, List<T> items) {
		return PageResponse.<T>builder()
			.offset(offset)
			.limit(limit)
			.total(total)
			.items(items)
			.build();
	}

    public <R> PageResponse<R> map(Function<? super T, ? extends R> converter) {
        List<R> newItems = this.items.stream()
                .map(converter)
                .collect(Collectors.toList());
        return PageResponse.<R>builder()
                .offset(this.offset)
                .limit(this.limit)
                .total(this.total)
                .items(newItems)
                .build();
    }
}
