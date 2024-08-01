package com.project.singk.domain.post.domain;

import com.project.singk.domain.album.domain.GenreType;
import com.project.singk.domain.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RecommendPost {

    private final Long id;
    private final String title;
    private final String content;
    private final RecommendType recommend;
    private final GenreType genre;
    private final String link;
    private final int likes;
    private final int comments;
    private final Member member;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    @Builder
    public RecommendPost(Long id, String title, String content, RecommendType recommend, GenreType genre, String link, int likes, int comments, Member member, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.recommend = recommend;
        this.genre = genre;
        this.link = link;
        this.likes = likes;
        this.comments = comments;
        this.member = member;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static RecommendPost albumType(RecommendPostCreate recommendPostCreate, String link, Member member) {
        return RecommendPost.builder()
                .title(recommendPostCreate.getTitle())
                .content(recommendPostCreate.getContent())
                .recommend(RecommendType.ALBUM)
                .link(link)
                .genre(GenreType.valueOf(recommendPostCreate.getGenre()))
                .member(member)
                .build();
    }

    public static RecommendPost imageType(RecommendPostCreate recommendPostCreate, String key, Member member) {
        return RecommendPost.builder()
                .title(recommendPostCreate.getTitle())
                .content(recommendPostCreate.getContent())
                .recommend(RecommendType.IMAGE)
                .link(key)
                .genre(GenreType.valueOf(recommendPostCreate.getGenre()))
                .member(member)
                .build();
    }
    public static RecommendPost youtubeType(RecommendPostCreate recommendPostCreate, Member member) {
        return RecommendPost.builder()
                .title(recommendPostCreate.getTitle())
                .content(recommendPostCreate.getContent())
                .recommend(RecommendType.YOUTUBE)
                .link(recommendPostCreate.getLink())
                .genre(GenreType.valueOf(recommendPostCreate.getGenre()))
                .member(member)
                .build();
    }

    public RecommendPost update(RecommendPostUpdate recommendPostUpdate){
        return RecommendPost.builder()
                .id(this.id)
                .title(recommendPostUpdate.getTitle() == null ? this.title : recommendPostUpdate.getTitle())
                .content(recommendPostUpdate.getContent() == null ? this.content : recommendPostUpdate.getContent())
                .genre(this.genre)
                .recommend(this.recommend)
                .likes(this.likes)
                .comments(this.comments)
                .link(this.link)
                .member(this.member)
                .createdAt(this.createdAt)
                .modifiedAt(this.modifiedAt)
                .build();
    }
    public RecommendPost updateComments(int comments){
        return RecommendPost.builder()
                .id(this.id)
                .title(this.title)
                .content(this.title)
                .genre(this.genre)
                .recommend(this.recommend)
                .likes(this.likes)
                .comments(comments)
                .link(this.link)
                .member(this.member)
                .createdAt(this.createdAt)
                .modifiedAt(this.modifiedAt)
                .build();
    }
    public RecommendPost updateLikes(int likes){
        return RecommendPost.builder()
                .id(this.id)
                .title(this.title)
                .content(this.title)
                .genre(this.genre)
                .recommend(this.recommend)
                .likes(likes)
                .comments(this.comments)
                .link(this.link)
                .member(this.member)
                .createdAt(this.createdAt)
                .modifiedAt(this.modifiedAt)
                .build();
    }
}
