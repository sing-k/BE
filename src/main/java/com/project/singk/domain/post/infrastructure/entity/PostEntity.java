package com.project.singk.domain.post.infrastructure.entity;

import com.project.singk.domain.member.infrastructure.MemberEntity;
import com.project.singk.domain.post.domain.Post;
import com.project.singk.domain.post.domain.PostType;
import com.project.singk.global.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Posts")
@Getter
@NoArgsConstructor
public class PostEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "post_type")
    private PostType postType;

    @Column(name = "likes")
    private Integer likes = 0;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity member;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Builder
    public PostEntity(Long id, String title, String content, PostType postType, Integer likes, Boolean isDeleted, MemberEntity member, String thumbnailUrl) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.postType = postType;
        this.likes = likes;
        this.isDeleted = isDeleted;
        this.member = member;
        this.thumbnailUrl = thumbnailUrl;
    }

    public static PostEntity from(Post post) {
        return PostEntity.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .postType(post.getPostType())
                .likes(post.getLikes())
                .isDeleted(post.getIsDeleted())
                .member(MemberEntity.from(post.getMember()))
                .build();
    }

    public Post toModel() {
        return Post.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .postType(this.postType)
                .likes(this.likes)
                .isDeleted(this.isDeleted)
                .member(this.member.toModel())
                .createdAt(this.getCreatedAt())
                .modifiedAt(this.getModifiedAt())
                .thumbnailUrl(this.getThumbnailUrl())
                .build();
    }
}
