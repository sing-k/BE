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
public class PageResponse<T> {

    // 기본 값일 때 응답에 미포함
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
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

    public static <T> PageResponse<T> of (int limit, int total, List<T> items) {
		return PageResponse.<T>builder()
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
