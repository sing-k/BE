package com.project.singk.domain.comment.domain;

import com.project.singk.domain.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentSimplified {
    private final Long id;
    private final Long parentId;
    private final String content;
    private final int likes;
    private final Member member;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    @Builder
    public CommentSimplified(Long id, Long parentId, String content, int likes, Member member, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.parentId = parentId;
        this.content = content;
        this.likes = likes;
        this.member = member;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
