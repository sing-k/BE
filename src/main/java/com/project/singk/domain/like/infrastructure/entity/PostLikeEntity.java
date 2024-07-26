package com.project.singk.domain.like.infrastructure.entity;

import com.project.singk.domain.like.domain.PostLike;
import com.project.singk.domain.member.infrastructure.MemberEntity;
import com.project.singk.domain.post.infrastructure.entity.PostEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="PostLikes")
@Getter
@NoArgsConstructor
public class PostLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id",nullable = false)
    private MemberEntity member;

    @ManyToOne
    @JoinColumn(name="post_id",nullable = false)
    private PostEntity post;

    @Builder
    public PostLikeEntity(Long id, MemberEntity member, PostEntity post){
        this.id = id;
        this.member = member;
        this.post = post;
    }

    public static PostLikeEntity from(PostLike like){
        return PostLikeEntity.builder()
                .id(like.getId())
                .member(MemberEntity.from(like.getMember()))
                .post(PostEntity.from(like.getPost()))
                .build();
    }

    public PostLike toModel(){
        return PostLike.builder()
                .id(this.id)
                .member(this.member.toModel())
                .post(this.post.toModel())
                .build();
    }

}

