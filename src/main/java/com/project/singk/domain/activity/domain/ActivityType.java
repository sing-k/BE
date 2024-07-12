package com.project.singk.domain.activity.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActivityType {
    ATTENDANCE(30, "출석하기"),
    /**
        앨범 감상평 관련
     */
    WRITE_ALBUM_REVIEW(10, "앨범 감상평 작성하기"),
    RECOMMENDED_ALBUM_REVIEW(50, "앨범 감상평 공감받기"),
    REACT_ALBUM_REVIEW(5, "앨범 감상평 공감하기"),
    DELETE_ALBUM_REVIEW(-50, "앨범 감상평 삭제하기"),
    /**
         게시글 관련
     */
    WRITE_POST(10, "게시글 작성하기"),
    RECOMMENDED_POST(10, "게시글 공감받기"),
    REACT_POST(3, "게시글 공감하기"),
    DELETE_POST(-25, "게시글 삭제하기"),
    /**
        댓글 관련
     */
    WRITE_COMMENT(1, "댓글 작성하기"),
    RECOMMENDED_COMMENT(10, "댓글 공감받기"),
    REACT_COMMENT(1, "댓글 공감하기"),
    DELETE_COMMENT(-5, "댓글 삭제하기");

    private final int score;
    private final String content;
}
