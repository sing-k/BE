package com.project.singk.domain.like.infrastructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostCommentLikeEntity is a Querydsl query type for PostCommentLikeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostCommentLikeEntity extends EntityPathBase<PostCommentLikeEntity> {

    private static final long serialVersionUID = -839850248L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostCommentLikeEntity postCommentLikeEntity = new QPostCommentLikeEntity("postCommentLikeEntity");

    public final com.project.singk.domain.comment.infrastructure.entity.QPostCommentEntity comment;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.project.singk.domain.member.infrastructure.QMemberEntity member;

    public QPostCommentLikeEntity(String variable) {
        this(PostCommentLikeEntity.class, forVariable(variable), INITS);
    }

    public QPostCommentLikeEntity(Path<? extends PostCommentLikeEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostCommentLikeEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostCommentLikeEntity(PathMetadata metadata, PathInits inits) {
        this(PostCommentLikeEntity.class, metadata, inits);
    }

    public QPostCommentLikeEntity(Class<? extends PostCommentLikeEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.comment = inits.isInitialized("comment") ? new com.project.singk.domain.comment.infrastructure.entity.QPostCommentEntity(forProperty("comment"), inits.get("comment")) : null;
        this.member = inits.isInitialized("member") ? new com.project.singk.domain.member.infrastructure.QMemberEntity(forProperty("member"), inits.get("member")) : null;
    }

}

