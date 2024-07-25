package com.project.singk.domain.post.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RecommendType {
    IMAGE("이미지"),
    ALBUM("앨범"),
    YOUTUBE("유튜브");

    private final String name;

}
