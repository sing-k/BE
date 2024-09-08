package com.project.singk.domain.comment.infrastructure.entity;

import com.project.singk.domain.comment.domain.RecommendComment;
import com.project.singk.domain.comment.domain.CommentSimplified;
import com.project.singk.domain.member.infrastructure.MemberEntity;
import com.project.singk.domain.post.infrastructure.entity.RecommendPostEntity;
import com.project.singk.global.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "RECOMMEND_COMMENTS")
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
    private int likes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private RecommendPostEntity post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private RecommendCommentEntity parent;

    @OneToMany(mappedBy = "parent")
    private List<RecommendCommentEntity> children;

    @Builder
    public RecommendCommentEntity(Long id, String content, int likes, RecommendPostEntity post, RecommendCommentEntity parent, MemberEntity member, List<RecommendCommentEntity> children){
        this.id = id;
        this.content = content;
        this.likes = likes;
        this.post = post;
        this.parent = parent;
        this.member = member;
        this.children = children;
    }

    public static RecommendCommentEntity from(RecommendComment comment){
        return RecommendCommentEntity.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .member(MemberEntity.from(comment.getMember()))
                .post(RecommendPostEntity.from(comment.getPost()))
                .parent(comment.getParentId() == null ? null : RecommendCommentEntity.id(comment.getParentId()))
                .likes(comment.getLikes())
                .build();
    }

    public static RecommendCommentEntity id(Long commentId) {
        return RecommendCommentEntity.builder()
                .id(commentId)
                .build();
    }

    public CommentSimplified simplified() {
        return CommentSimplified.builder()
                .id(this.id)
                .parentId(this.parent == null ? null : this.parent.getId())
                .postId(this.post.getId())
                .content(this.content)
                .likes(this.likes)
                .member(this.member.toModel())
                .createdAt(this.getCreatedAt())
                .modifiedAt(this.getModifiedAt())
                .build();

    }

    public RecommendComment toModel(){
        return RecommendComment.builder()
                .id(this.id)
                .parentId(this.parent == null ? null : this.parent.getId())
                .content(this.content)
                .likes(this.likes)
                .member(this.member.toModel())
                .post(this.post.toModel())
                .build();
    }
}
