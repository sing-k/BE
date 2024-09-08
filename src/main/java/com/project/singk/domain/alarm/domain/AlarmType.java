package com.project.singk.domain.alarm.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@Getter
@RequiredArgsConstructor
public enum AlarmType {

    // 작성
    WRITE_COMMENT_FREE_POST(
            (sender) -> String.format("%s님이 회원님의 게시글에 댓글을 작성했습니다.", sender),
            (id) -> String.format("/board/%d", id)
    ),
    WRITE_COMMENT_RECOMMEND_POST(
            (sender) -> String.format("%s님이 회원님의 게시글에 댓글을 작성했습니다.", sender),
            (id) -> String.format("/musicRecommendationBoard/%d", id)
    ),
    WRITE_REPLY_FREE_COMMENT(
            (sender) -> String.format("%s님이 회원님의 댓글에 대댓글을 작성했습니다.", sender),
            (id) -> String.format("/board/%d", id)
    ),
    WRITE_REPLY_RECOMMEND_COMMENT(
            (sender) -> String.format("%s님이 회원님의 게시글에 대댓글을 작성했습니다.", sender),
            (id) -> String.format("/musicRecommendationBoard/%d", id)
    ),

    // 좋아요
    FREE_POST_LIKE(
            (sender) -> String.format("%s님이 회원님의 게시글을 좋아합니다", sender),
            (id) -> String.format("/board/%d", id)
    ),
    RECOMMEND_POST_LIKE(
            (sender) -> String.format("%s님이 회원님의 게시글을 좋아합니다", sender),
            (id) -> String.format("/musicRecommendationBoard/%d", id)
    ),
    FREE_COMMENT_LIKE(
            (sender) -> String.format("%s님이 회원님의 댓글을 좋아합니다", sender),
            (id) -> String.format("/board/%d", id)
    ),
    RECOMMEND_COMMENT_LIKE(
            (sender) -> String.format("%s님이 회원님의 댓글을 좋아합니다", sender),
            (id) -> String.format("/musicRecommendationBoard/%d", id)
    );

    private final Function<String, String> content;
    private final Function<Long, String> url;
}
