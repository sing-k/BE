package com.project.singk.domain.post.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FreePostCreate {
    private String title;
    private String content;

    @Builder
    public FreePostCreate(
            @JsonProperty("title") String title,
            @JsonProperty("content") String content
    ){
        this.title = title;
        this.content = content;
    }
}
