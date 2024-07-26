package com.project.singk.domain.like.domain;

import com.project.singk.domain.comment.domain.PostComment;
import com.project.singk.domain.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostCommentLike {
    private Long id;
    private Member member;
    private PostComment comment;

    @Builder
    public PostCommentLike(Long id,Member member,PostComment comment){
        this.id = id;
        this.member = member;
        this.comment = comment;
    }
}
