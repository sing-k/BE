package com.project.singk.global.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CursorPageResponse<T> {

	private int limit;
    private boolean hasNext;
	private List<T> items;

    public static <T> CursorPageResponse<T> of (int limit, boolean hasNext, List<T> items) {
		return CursorPageResponse.<T>builder()
			.limit(limit)
            .hasNext(hasNext)
			.items(items)
			.build();
	}

    public <R> CursorPageResponse<R> map(Function<? super T, ? extends R> converter) {
        List<R> newItems = this.items.stream()
                .map(converter)
                .collect(Collectors.toList());
        return CursorPageResponse.<R>builder()
                .limit(this.limit)
                .hasNext(this.hasNext)
                .items(newItems)
                .build();
    }
}
