package com.project.singk.domain.post.domain;

import com.project.singk.domain.album.domain.AlbumGenre;
import com.project.singk.domain.album.domain.GenreType;
import com.project.singk.domain.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Post {
    private Long id;
    private String title;
    private String content;
    private Integer likes;
    private Boolean isDeleted;
    private Member member;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Builder
    public Post(Long id, String title, String content, Integer likes, Boolean isDeleted, Member member, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.likes = likes;
        this.isDeleted = isDeleted;
        this.member = member;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static Post byRequest(PostCreate req, Member member) {
        return Post.builder()
                .title(req.getTitle())
                .content(req.getTitle())
                .member(member)
                .build();
    }
}
