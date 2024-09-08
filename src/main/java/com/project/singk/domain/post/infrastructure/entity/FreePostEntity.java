package com.project.singk.domain.post.infrastructure.entity;

import com.project.singk.domain.member.infrastructure.MemberEntity;
import com.project.singk.domain.post.domain.FreePost;
import com.project.singk.global.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "FREE_POSTS")
@Getter
@NoArgsConstructor
public class FreePostEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "likes")
    @ColumnDefault("0")
    private int likes;

    @Column(name = "comments")
    @ColumnDefault("0")
    private int comments;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity member;

    @Builder
    public FreePostEntity(Long id, String title, String content, int likes, int comments, MemberEntity member) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.likes = likes;
        this.comments = comments;
        this.member = member;
    }

    public static FreePostEntity from(FreePost freePost) {
        return FreePostEntity.builder()
                .id(freePost.getId())
                .title(freePost.getTitle())
                .content(freePost.getContent())
                .likes(freePost.getLikes())
                .comments(freePost.getComments())
                .member(MemberEntity.from(freePost.getMember()))
                .build();
    }

    public FreePost toModel() {
        return FreePost.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .likes(this.likes)
                .comments(this.comments)
                .member(this.member.toModel())
                .createdAt(this.getCreatedAt())
                .modifiedAt(this.getModifiedAt())
                .build();
    }
}
