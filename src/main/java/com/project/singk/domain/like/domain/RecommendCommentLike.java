package com.project.singk.domain.like.domain;

import com.project.singk.domain.comment.domain.RecommendComment;
import com.project.singk.domain.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RecommendCommentLike {
    private Long id;
    private Member member;
    private RecommendComment comment;

    @Builder
    public RecommendCommentLike(Long id,Member member,RecommendComment comment){
        this.id = id;
        this.member = member;
        this.comment = comment;
    }
}
