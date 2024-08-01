package com.project.singk.domain.like.infrastructure.entity;

import com.project.singk.domain.like.domain.FreePostLike;
import com.project.singk.domain.member.infrastructure.MemberEntity;
import com.project.singk.domain.post.infrastructure.entity.FreePostEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="FREE_POST_LIKES")
@Getter
@NoArgsConstructor
public class FreePostLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id",nullable = false)
    private MemberEntity member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id",nullable = false)
    private FreePostEntity post;

    @Builder
    public FreePostLikeEntity(Long id, MemberEntity member, FreePostEntity post){
        this.id = id;
        this.member = member;
        this.post = post;
    }

    public static FreePostLikeEntity from(FreePostLike like){
        return FreePostLikeEntity.builder()
                .id(like.getId())
                .member(MemberEntity.from(like.getMember()))
                .post(FreePostEntity.from(like.getPost()))
                .build();
    }

    public FreePostLike toModel(){
        return FreePostLike.builder()
                .id(this.id)
                .member(this.member.toModel())
                .post(this.post.toModel())
                .build();
    }

}

