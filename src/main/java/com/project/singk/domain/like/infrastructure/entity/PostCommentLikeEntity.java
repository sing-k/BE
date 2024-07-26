package com.project.singk.domain.like.infrastructure.entity;

import com.project.singk.domain.comment.infrastructure.entity.PostCommentEntity;
import com.project.singk.domain.like.domain.PostCommentLike;
import com.project.singk.domain.member.infrastructure.MemberEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="PostCommentLikes")
@Getter
@NoArgsConstructor
public class PostCommentLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id",nullable = false)
    private MemberEntity member;

    @ManyToOne
    @JoinColumn(name="comment_id",nullable = false)
    private PostCommentEntity comment;

    @Builder
    public PostCommentLikeEntity(Long id, MemberEntity member,PostCommentEntity comment){
        this.id = id;
        this.member = member;
        this.comment = comment;
    }

    public static PostCommentLikeEntity from(PostCommentLike like){
        return PostCommentLikeEntity.builder()
                .id(like.getId())
                .member(MemberEntity.from(like.getMember()))
                .comment(PostCommentEntity.from(like.getComment()))
                .build();
    }

    public PostCommentLike toModel(){
        return PostCommentLike.builder()
                .id(this.id)
                .member(this.member.toModel())
                .comment(this.comment.toModel())
                .build();
    }

}
