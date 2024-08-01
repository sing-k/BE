package com.project.singk.domain.like.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class LikeResponse {
    private int count;
    private boolean isLike;

    public static LikeResponse from (int count, boolean isLike) {
        return LikeResponse.builder()
                .count(count)
                .isLike(isLike)
                .build();
    }
}
