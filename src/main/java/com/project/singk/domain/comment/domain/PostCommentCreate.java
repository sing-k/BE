package com.project.singk.domain.comment.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostCommentCreate {
    private final String content;

    @Builder
    public PostCommentCreate(
            @JsonProperty("content")String content
    ){
        this.content = content;
    }
}
