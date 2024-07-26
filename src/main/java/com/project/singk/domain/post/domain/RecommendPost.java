package com.project.singk.domain.post.domain;

import com.project.singk.domain.album.domain.GenreType;
import com.project.singk.domain.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class RecommendPost {

    private Long id;
    private String title;
    private String content;
    private RecommendType recommendType;
    private Integer likes;
    private Integer comments;
    private Boolean isDeleted;
    private Member member;
    private String thumbnailUrl;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private GenreType genre;

    @Builder
    public RecommendPost(Long id, String title, String content, RecommendType recommendType, Integer likes, Integer comments, Boolean isDeleted, Member member, String thumbnailUrl, LocalDateTime createdAt, LocalDateTime modifiedAt,GenreType genre) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.recommendType = recommendType;
        this.likes = likes;
        this.comments = comments;
        this.isDeleted = isDeleted;
        this.member = member;
        this.thumbnailUrl = thumbnailUrl;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.genre = genre;
    }

    public static RecommendPost byRequest(RecommendPostCreate req, Member member) {
        return RecommendPost.builder()
                .title(req.getTitle())
                .content(req.getTitle())
                .recommendType(RecommendType.valueOf(req.getType()))
                .thumbnailUrl(req.getLink())
                .genre(GenreType.valueOf(req.getGenre()))
                .member(member)
                .build();
    }
    public void update(RecommendPostUpdate req){
        String newTitle = req.getTitle();
        String newContent = req.getContent();
        if(!newTitle.isEmpty()){
            title = newTitle;
        }
        if(!newContent.isEmpty()){
            content = newContent;
        }
    }
    public void updateCommentCount(int cnt){
        this.comments = cnt;
    }
    public void updateLikeCount(int cnt){
        this.likes = cnt;
    }
}
