package com.project.singk.domain.like.infrastructure.entity;

import com.project.singk.domain.comment.infrastructure.entity.RecommendCommentEntity;
import com.project.singk.domain.like.domain.RecommendCommentLike;
import com.project.singk.domain.member.infrastructure.MemberEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="RecommendCommentLikes")
@Getter
@NoArgsConstructor
public class RecommendCommentLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id",nullable = false)
    private MemberEntity member;

    @ManyToOne
    @JoinColumn(name="comment_id",nullable = false)
    private RecommendCommentEntity comment;

    @Builder
    public RecommendCommentLikeEntity(Long id, MemberEntity member,RecommendCommentEntity comment){
        this.id = id;
        this.member = member;
        this.comment = comment;
    }

    public static RecommendCommentLikeEntity from(RecommendCommentLike like){
        return RecommendCommentLikeEntity.builder()
                .id(like.getId())
                .member(MemberEntity.from(like.getMember()))
                .comment(RecommendCommentEntity.from(like.getComment()))
                .build();
    }

    public RecommendCommentLike toModel(){
        return RecommendCommentLike.builder()
                .id(this.id)
                .member(this.member.toModel())
                .comment(this.comment.toModel())
                .build();
    }

}
