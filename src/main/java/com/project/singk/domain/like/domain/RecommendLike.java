package com.project.singk.domain.like.domain;

import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.post.domain.RecommendPost;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RecommendLike {
    private Long id;
    private Member member;
    private RecommendPost post;

    @Builder
    public RecommendLike(Long id,Member member,RecommendPost post){
        this.id = id;
        this.member = member;
        this.post = post;
    }
}
