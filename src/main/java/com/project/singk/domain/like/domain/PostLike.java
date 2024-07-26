package com.project.singk.domain.like.domain;

import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.post.domain.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostLike {
    private Long id;
    private Member member;
    private Post post;

    @Builder
    public PostLike(Long id,Member member,Post post){
        this.id = id;
        this.member = member;
        this.post = post;
    }
}
