package com.project.singk.domain.comment.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentCreate {
    private final String content;

    @JsonCreator
    @Builder
    public CommentCreate(
            @JsonProperty("content")String content
    ) {
        this.content = content;
    }
}
