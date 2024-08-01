package com.project.singk.domain.comment.domain;

import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.post.domain.FreePost;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FreeComment {
    private final Long id;
    private final Long parentId;
    private final String content;
    private final int likes;
    private final Member member;
    private final FreePost post;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    @Builder
    public FreeComment(Long id, Long parentId, String content, int likes, Member member, FreePost post, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.parentId = parentId;
        this.content = content;
        this.likes = likes;
        this.member = member;
        this.post = post;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static FreeComment from(CommentCreate commentCreate, Member member, FreePost post, Long parentId){
        return FreeComment.builder()
                .parentId(parentId)
                .content(commentCreate.getContent())
                .member(member)
                .post(post)
                .build();
    }

    public FreeComment update(CommentCreate commentCreate){
        return FreeComment.builder()
                .id(this.id)
                .parentId(this.parentId)
                .content(commentCreate.getContent() == null ? this.content : commentCreate.getContent())
                .likes(this.likes)
                .member(this.member)
                .post(this.post)
                .createdAt(this.createdAt)
                .modifiedAt(this.modifiedAt)
                .build();
    }
    public FreeComment updateLikes(int likes){
        return FreeComment.builder()
                .id(this.id)
                .parentId(this.parentId)
                .content(this.content)
                .likes(likes)
                .member(this.member)
                .post(this.post)
                .createdAt(this.createdAt)
                .modifiedAt(this.modifiedAt)
                .build();
    }
}
