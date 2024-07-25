package com.project.singk.domain.comment.infrastructure.entity;

import com.project.singk.domain.comment.domain.RecommendComment;
import com.project.singk.domain.member.infrastructure.MemberEntity;
import com.project.singk.domain.post.infrastructure.entity.RecommendPostEntity;
import com.project.singk.global.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name= "Comments")
@Getter
@NoArgsConstructor
public class RecommendCommentEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="content")
    private String content;

    @ColumnDefault("0")
    @Column(name="likes")
    private int likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id",nullable = false)
    private RecommendPostEntity post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id")
    private RecommendCommentEntity parent;

    @Builder
    public RecommendCommentEntity(Long id, String content, int likeCount, RecommendPostEntity post, RecommendCommentEntity parent, MemberEntity member){
        this.id = id;
        this.content = content;
        this.likeCount = likeCount;
        this.post = post;
        this.parent = parent;
        this.member = member;
    }

    public static RecommendCommentEntity from(RecommendComment comment){
        return RecommendCommentEntity.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .member(MemberEntity.from(comment.getMember()))
                .post(RecommendPostEntity.from(comment.getPost()))
                .parent(RecommendCommentEntity.from(comment.getParent()))
                .likeCount(comment.getLikeCount())
                .build();
    }

    public RecommendComment toModel(){
        return RecommendComment.builder()
                .id(this.id)
                .content(this.content)
                .member(this.member.toModel())
                .likeCount(this.likeCount)
                .parent(this.parent.toModel())
                .post(this.post.toModel())
                .build();
    }
}
