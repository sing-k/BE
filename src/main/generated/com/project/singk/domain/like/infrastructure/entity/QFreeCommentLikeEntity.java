package com.project.singk.domain.like.infrastructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFreeCommentLikeEntity is a Querydsl query type for FreeCommentLikeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFreeCommentLikeEntity extends EntityPathBase<FreeCommentLikeEntity> {

    private static final long serialVersionUID = 1124022956L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFreeCommentLikeEntity freeCommentLikeEntity = new QFreeCommentLikeEntity("freeCommentLikeEntity");

    public final com.project.singk.domain.comment.infrastructure.entity.QFreeCommentEntity comment;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.project.singk.domain.member.infrastructure.QMemberEntity member;

    public QFreeCommentLikeEntity(String variable) {
        this(FreeCommentLikeEntity.class, forVariable(variable), INITS);
    }

    public QFreeCommentLikeEntity(Path<? extends FreeCommentLikeEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFreeCommentLikeEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFreeCommentLikeEntity(PathMetadata metadata, PathInits inits) {
        this(FreeCommentLikeEntity.class, metadata, inits);
    }

    public QFreeCommentLikeEntity(Class<? extends FreeCommentLikeEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.comment = inits.isInitialized("comment") ? new com.project.singk.domain.comment.infrastructure.entity.QFreeCommentEntity(forProperty("comment"), inits.get("comment")) : null;
        this.member = inits.isInitialized("member") ? new com.project.singk.domain.member.infrastructure.QMemberEntity(forProperty("member"), inits.get("member")) : null;
    }

}

