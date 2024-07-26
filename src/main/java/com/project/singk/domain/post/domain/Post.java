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
    private Integer comments;
    private Boolean isDeleted;
    private Member member;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Builder
    public Post(Long id, String title, String content, Integer likes, Integer comments, Boolean isDeleted, Member member, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.likes = likes;
        this.isDeleted = isDeleted;
        this.member = member;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.comments = comments;
    }

    public static Post byRequest(PostCreate req, Member member) {
        return Post.builder()
                .title(req.getTitle())
                .content(req.getTitle())
                .member(member)
                .build();
    }

    public void update(PostCreate req){
        String newTitle = req.getTitle();
        String newContent = req.getContent();
        if(!newTitle.isEmpty()){
            title = newTitle;
        }
        if(!newContent.isEmpty()){
            content = newContent;
        }
    }
    public void updateLikeCount(int cnt){
        this.likes = cnt;
    }

    public void updateCommentCount(int cnt){
        this.comments = cnt;
    }

}
