package com.project.singk.domain.comment.infrastructure.entity;

import com.project.singk.domain.comment.domain.PostComment;
import com.project.singk.domain.comment.domain.RecommendComment;
import com.project.singk.domain.member.infrastructure.MemberEntity;
import com.project.singk.domain.post.infrastructure.entity.PostEntity;
import com.project.singk.domain.post.infrastructure.entity.RecommendPostEntity;
import com.project.singk.global.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name= "PostComments")
@Getter
@NoArgsConstructor
public class PostCommentEntity extends BaseTimeEntity {

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
    private PostEntity post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id")
    private PostCommentEntity parent;

    @Builder
    public PostCommentEntity(Long id, String content, int likeCount, PostEntity post, PostCommentEntity parent, MemberEntity member){
        this.id = id;
        this.content = content;
        this.likeCount = likeCount;
        this.post = post;
        this.parent = parent;
        this.member = member;
    }

    public static PostCommentEntity from(PostComment comment){
        return PostCommentEntity.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .member(MemberEntity.from(comment.getMember()))
                .post(PostEntity.from(comment.getPost()))
                .parent(PostCommentEntity.from(comment.getParent()))
                .likeCount(comment.getLikeCount())
                .build();
    }

    public PostComment toModel(){
        return PostComment.builder()
                .id(this.id)
                .content(this.content)
                .member(this.member.toModel())
                .likeCount(this.likeCount)
                .parent(this.parent.toModel())
                .post(this.post.toModel())
                .build();
    }
}

