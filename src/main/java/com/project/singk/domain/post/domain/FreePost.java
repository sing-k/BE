package com.project.singk.domain.post.domain;

import com.project.singk.domain.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FreePost {
    private final Long id;
    private final String title;
    private final String content;
    private final int likes;
    private final int comments;
    private final Member member;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    @Builder
    public FreePost(Long id, String title, String content, int likes, int comments, Member member, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.likes = likes;
        this.member = member;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.comments = comments;
    }

    public static FreePost from(FreePostCreate freePostCreate, Member member) {
        return FreePost.builder()
                .title(freePostCreate.getTitle())
                .content(freePostCreate.getTitle())
                .member(member)
                .build();
    }

    public FreePost update(FreePostCreate freePostCreate){
        return FreePost.builder()
                .id(this.id)
                .title(freePostCreate.getTitle() == null ? this.title : freePostCreate.getTitle())
                .content(freePostCreate.getContent() == null ? this.content : freePostCreate.getContent())
                .likes(this.likes)
                .comments(this.comments)
                .member(this.member)
                .createdAt(this.createdAt)
                .modifiedAt(this.modifiedAt)
                .build();

    }
    public FreePost updateLikes(int likes){
        return FreePost.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .likes(likes)
                .comments(this.comments)
                .member(this.member)
                .createdAt(this.createdAt)
                .modifiedAt(this.modifiedAt)
                .build();
    }

    public FreePost updateComments(int comments){
        return FreePost.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .likes(likes)
                .comments(this.comments)
                .member(this.member)
                .createdAt(this.createdAt)
                .modifiedAt(this.modifiedAt)
                .build();
    }

}
