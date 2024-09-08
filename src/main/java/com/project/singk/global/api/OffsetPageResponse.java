package com.project.singk.global.api;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OffsetPageResponse<T> {

	private int offset;
	private int limit;
	private int total;
	private List<T> items;

	public static <T> OffsetPageResponse<T> of (int offset, int limit, int total, List<T> items) {
		return OffsetPageResponse.<T>builder()
			.offset(offset)
			.limit(limit)
			.total(total)
			.items(items)
			.build();
	}

    public <R> OffsetPageResponse<R> map(Function<? super T, ? extends R> converter) {
        List<R> newItems = this.items.stream()
                .map(converter)
                .collect(Collectors.toList());
        return OffsetPageResponse.<R>builder()
                .offset(this.offset)
                .limit(this.limit)
                .total(this.total)
                .items(newItems)
                .build();
    }
}
