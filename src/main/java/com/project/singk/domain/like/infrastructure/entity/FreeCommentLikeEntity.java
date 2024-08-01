package com.project.singk.domain.like.infrastructure.entity;

import com.project.singk.domain.comment.infrastructure.entity.FreeCommentEntity;
import com.project.singk.domain.like.domain.FreeCommentLike;
import com.project.singk.domain.member.infrastructure.MemberEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="FREE_COMMENT_LIKES")
@Getter
@NoArgsConstructor
public class FreeCommentLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id", nullable = false)
    private MemberEntity member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="comment_id", nullable = false)
    private FreeCommentEntity comment;

    @Builder
    public FreeCommentLikeEntity(Long id, MemberEntity member, FreeCommentEntity comment){
        this.id = id;
        this.member = member;
        this.comment = comment;
    }

    public static FreeCommentLikeEntity from(FreeCommentLike like){
        return FreeCommentLikeEntity.builder()
                .id(like.getId())
                .member(MemberEntity.from(like.getMember()))
                .comment(FreeCommentEntity.from(like.getComment()))
                .build();
    }

    public FreeCommentLike toModel(){
        return FreeCommentLike.builder()
                .id(this.id)
                .member(this.member.toModel())
                .comment(this.comment.toModel())
                .build();
    }

}
