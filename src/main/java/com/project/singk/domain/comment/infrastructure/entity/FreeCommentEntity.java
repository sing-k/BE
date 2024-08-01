package com.project.singk.domain.comment.infrastructure.entity;

import com.project.singk.domain.comment.domain.CommentSimplified;
import com.project.singk.domain.comment.domain.FreeComment;
import com.project.singk.domain.member.infrastructure.MemberEntity;
import com.project.singk.domain.post.infrastructure.entity.FreePostEntity;
import com.project.singk.global.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Table(name= "FREE_COMMENTS")
@Getter
@NoArgsConstructor
public class FreeCommentEntity extends BaseTimeEntity {

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
    private FreePostEntity post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private FreeCommentEntity parent;

    @OneToMany(mappedBy = "parent")
    private List<FreeCommentEntity> children;

    @Builder
    public FreeCommentEntity(Long id, String content, int likes, FreePostEntity post, FreeCommentEntity parent, MemberEntity member, List<FreeCommentEntity> children){
        this.id = id;
        this.content = content;
        this.likes = likes;
        this.post = post;
        this.parent = parent;
        this.member = member;
        this.children = children;
    }

    public static FreeCommentEntity from(FreeComment comment){
        return FreeCommentEntity.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .member(MemberEntity.from(comment.getMember()))
                .post(FreePostEntity.from(comment.getPost()))
                .parent(comment.getParentId() == null ? null : FreeCommentEntity.id(comment.getParentId()))
                .likes(comment.getLikes())
                .build();
    }

    public static FreeCommentEntity id(Long commentId) {
        return FreeCommentEntity.builder()
                .id(commentId)
                .build();
    }

    public CommentSimplified simplified() {
        return CommentSimplified.builder()
                .id(this.id)
                .parentId(this.parent == null ? null : this.parent.getId())
                .content(this.content)
                .likes(this.likes)
                .member(this.member.toModel())
                .createdAt(this.getCreatedAt())
                .modifiedAt(this.getModifiedAt())
                .build();

    }

    public FreeComment toModel(){
        return FreeComment.builder()
                .id(this.id)
                .parentId(this.parent == null ? null : this.parent.getId())
                .content(this.content)
                .likes(this.likes)
                .member(this.member.toModel())
                .post(this.post.toModel())
                .build();
    }
}

