package com.project.singk.domain.like.domain;

import com.project.singk.domain.comment.domain.FreeComment;
import com.project.singk.domain.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FreeCommentLike {
    private final Long id;
    private final Member member;
    private final FreeComment comment;

    @Builder
    public FreeCommentLike(Long id, Member member, FreeComment comment){
        this.id = id;
        this.member = member;
        this.comment = comment;
    }

    public static FreeCommentLike from(Member member, FreeComment comment) {
        return FreeCommentLike.builder()
                .member(member)
                .comment(comment)
                .build();
    }
}
