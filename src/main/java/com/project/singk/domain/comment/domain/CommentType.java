package com.project.singk.domain.comment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommentType {
    FREE("자유 게시글"),
    RECOMMEND("앨범 추천 게시글");

    private final String name;
}
