package com.project.singk.domain.like.infrastructure.entity;

import com.project.singk.domain.like.domain.RecommendLike;
import com.project.singk.domain.member.infrastructure.MemberEntity;
import com.project.singk.domain.post.infrastructure.entity.RecommendPostEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="RecommendLikes")
@Getter
@NoArgsConstructor
public class RecommendLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id",nullable = false)
    private MemberEntity member;

    @ManyToOne
    @JoinColumn(name="post_id",nullable = false)
    private RecommendPostEntity post;

    @Builder
    public RecommendLikeEntity(Long id, MemberEntity member, RecommendPostEntity post){
        this.id = id;
        this.member = member;
        this.post = post;
    }

    public static RecommendLikeEntity from(RecommendLike like){
        return RecommendLikeEntity.builder()
                .id(like.getId())
                .member(MemberEntity.from(like.getMember()))
                .post(RecommendPostEntity.from(like.getPost()))
                .build();
    }

    public RecommendLike toModel(){
        return RecommendLike.builder()
                .id(this.id)
                .member(this.member.toModel())
                .post(this.post.toModel())
                .build();
    }

}
