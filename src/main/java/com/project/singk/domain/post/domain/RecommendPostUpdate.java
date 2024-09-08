package com.project.singk.domain.post.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RecommendPostUpdate {
    private final String title;
    private final String content;

    @Builder
    public RecommendPostUpdate(
            @JsonProperty("title") String title,
            @JsonProperty("content") String content
    ) {
        this.title = title;
        this.content = content;
    }
}
