package com.project.singk.domain.like.infrastructure.entity;

import com.project.singk.domain.like.domain.RecommendPostLike;
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
public class RecommendPostLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id", nullable = false)
    private MemberEntity member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id", nullable = false)
    private RecommendPostEntity post;

    @Builder
    public RecommendPostLikeEntity(Long id, MemberEntity member, RecommendPostEntity post){
        this.id = id;
        this.member = member;
        this.post = post;
    }

    public static RecommendPostLikeEntity from(RecommendPostLike like){
        return RecommendPostLikeEntity.builder()
                .id(like.getId())
                .member(MemberEntity.from(like.getMember()))
                .post(RecommendPostEntity.from(like.getPost()))
                .build();
    }

    public RecommendPostLike toModel(){
        return RecommendPostLike.builder()
                .id(this.id)
                .member(this.member.toModel())
                .post(this.post.toModel())
                .build();
    }

}
