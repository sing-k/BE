package com.project.singk.domain.like.domain;

import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.post.domain.FreePost;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FreePostLike {
    private final Long id;
    private final Member member;
    private final FreePost post;

    @Builder
    public FreePostLike(Long id, Member member, FreePost post){
        this.id = id;
        this.member = member;
        this.post = post;
    }

    public static FreePostLike from(Member member, FreePost freePost) {
        return FreePostLike.builder()
                .member(member)
                .post(freePost)
                .build();
    }
}
