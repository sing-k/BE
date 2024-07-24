package com.project.singk.domain.post.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RecommendPostCreate {
    private final String title;
    private final String content;
    private final String type;
    private final String link;

    @Builder
    public RecommendPostCreate(
            @JsonProperty("title") String title,
            @JsonProperty("content") String content,
            @JsonProperty("type") String type,
            @JsonProperty("link") String link
    ) {
        this.title = title;
        this.content = content;
        this.type = type;
        this.link = link;
    }
}
