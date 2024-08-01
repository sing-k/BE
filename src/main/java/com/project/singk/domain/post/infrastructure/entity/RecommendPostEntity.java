package com.project.singk.domain.post.infrastructure.entity;

import com.project.singk.domain.album.domain.GenreType;
import com.project.singk.domain.member.infrastructure.MemberEntity;
import com.project.singk.domain.post.domain.RecommendPost;
import com.project.singk.domain.post.domain.RecommendType;
import com.project.singk.global.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "RECOMMEND_POSTS")
@Getter
@NoArgsConstructor
public class RecommendPostEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "recommend_type")
    private RecommendType recommend;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre_type")
    private GenreType genre;

    @Column(name = "link")
    private String link;

    @ColumnDefault("0")
    @Column(name = "likes")
    private int likes;

    @ColumnDefault("0")
    @Column(name = "comments")
    private int comments;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity member;

    @Builder
    public RecommendPostEntity(Long id, String title, String content, String link, RecommendType recommend, int likes, Integer comments, GenreType genre, MemberEntity member) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.link = link;
        this.recommend = recommend;
        this.likes = likes;
        this.comments = comments;
        this.genre = genre;
        this.member = member;
    }

    public static RecommendPostEntity from(RecommendPost post) {
        return RecommendPostEntity.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .recommend(post.getRecommend())
                .genre(post.getGenre())
                .link(post.getLink())
                .likes(post.getLikes())
                .comments(post.getComments())
                .member(MemberEntity.from(post.getMember()))
                .build();
    }

    public RecommendPost toModel() {
        return RecommendPost.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .recommend(this.recommend)
                .genre(this.genre)
                .link(this.link)
                .likes(this.likes)
                .comments(this.comments)
                .member(this.member.toModel())
                .createdAt(this.getCreatedAt())
                .modifiedAt(this.getModifiedAt())
                .build();
    }
}

