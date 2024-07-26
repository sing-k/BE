package com.project.singk.domain.post.infrastructure.entity;

import com.project.singk.domain.album.domain.GenreType;
import com.project.singk.domain.member.infrastructure.MemberEntity;
import com.project.singk.domain.post.domain.Post;
import com.project.singk.domain.post.domain.RecommendPost;
import com.project.singk.domain.post.domain.RecommendType;
import com.project.singk.global.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Posts")
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
    private RecommendType recommendType;

    @Column(name = "likes")
    private Integer likes = 0;

    @Column(name = "comments")
    private Integer comments = 0;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre_type")
    private GenreType genre;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity member;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Builder
    public RecommendPostEntity(Long id, String title, String content, RecommendType recommendType, Integer likes, Integer comments, Boolean isDeleted, MemberEntity member, String thumbnailUrl,GenreType genre) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.recommendType = recommendType;
        this.likes = likes;
        this.comments = comments;
        this.isDeleted = isDeleted;
        this.member = member;
        this.thumbnailUrl = thumbnailUrl;
        this.genre = genre;
    }

    public static RecommendPostEntity from(RecommendPost post) {
        return RecommendPostEntity.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .recommendType(post.getRecommendType())
                .likes(post.getLikes())
                .comments(post.getComments())
                .isDeleted(post.getIsDeleted())
                .member(MemberEntity.from(post.getMember()))
                .genre(post.getGenre())
                .build();
    }

    public RecommendPost toModel() {
        return RecommendPost.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .recommendType(this.recommendType)
                .likes(this.likes)
                .comments(this.comments)
                .isDeleted(this.isDeleted)
                .member(this.member.toModel())
                .createdAt(this.getCreatedAt())
                .modifiedAt(this.getModifiedAt())
                .thumbnailUrl(this.getThumbnailUrl())
                .build();
    }
}

