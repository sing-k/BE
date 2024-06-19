package com.project.singk.domain.post.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public enum PostType {
    ALBUM_RECOMMENDATION("앨범 추천 게시글"),
    FREE("자유 게시글");

    private final String name;
}
