package com.project.singk.domain.like.domain;

import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.post.domain.RecommendPost;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RecommendPostLike {
    private final Long id;
    private final Member member;
    private final RecommendPost post;

    @Builder
    public RecommendPostLike(Long id, Member member, RecommendPost post){
        this.id = id;
        this.member = member;
        this.post = post;
    }

    public static RecommendPostLike from(Member member, RecommendPost post) {
        return RecommendPostLike.builder()
                .member(member)
                .post(post)
                .build();
    }
}
