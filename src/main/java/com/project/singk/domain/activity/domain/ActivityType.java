package com.project.singk.domain.activity.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActivityType {
    ATTENDANCE(30),
    /**
        앨범 감상평 관련
     */
    WRITE_ALBUM_REVIEW(10),
    RECOMMENDED_ALBUM_REVIEW(50),
    REACT_ALBUM_REVIEW(5),
    DELETE_ALBUM_REVIEW(-50),
    /**
         게시글 관련
     */
    WRITE_POST(10),
    RECOMMENDED_POST(10),
    REACT_POST(3),
    DELETE_POST(-25),
    /**
        댓글 관련
     */
    WRITE_COMMENT(1),
    RECOMMENDED_COMMENT(10),
    REACT_COMMENT(1),
    DELETE_COMMENT(-5);

    private final int score;
}
