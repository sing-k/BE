package com.project.singk.domain.post.domain;

import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.post.controller.request.PostCreateRequest;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Post {
    private Long id;
    private String title;
    private String content;
    private PostType postType;
    private Integer likes;
    private Boolean isDeleted;
    private Member member;
    private String thumbnailUrl;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Builder
    public Post(Long id, String title, String content, PostType postType, Integer likes, Boolean isDeleted, Member member, String thumbnailUrl, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.postType = postType;
        this.likes = likes;
        this.isDeleted = isDeleted;
        this.member = member;
        this.thumbnailUrl = thumbnailUrl;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static Post byRequest(PostCreateRequest req) {
        return Post.builder()
                .title(req.getTitle())
                .content(req.getTitle())
                .build();
    }
}
