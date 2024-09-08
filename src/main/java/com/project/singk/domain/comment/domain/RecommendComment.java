package com.project.singk.domain.comment.domain;

import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.post.domain.RecommendPost;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RecommendComment {
    private final Long id;
    private final Long parentId;
    private final String content;
    private final int likes;
    private final Member member;
    private final RecommendPost post;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    @Builder
    public RecommendComment(Long id, Long parentId, String content, int likes, Member member, RecommendPost post, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.parentId = parentId;
        this.content = content;
        this.likes = likes;
        this.member = member;
        this.post = post;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static RecommendComment from(CommentCreate commentCreate, Member member, RecommendPost post, Long parentId) {
        return RecommendComment.builder()
                .parentId(parentId)
                .content(commentCreate.getContent())
                .post(post)
                .member(member)
                .build();
    }

    public RecommendComment update(CommentCreate commentCreate){
        return RecommendComment.builder()
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
    public RecommendComment updateLikes(int likes){
        return RecommendComment.builder()
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
