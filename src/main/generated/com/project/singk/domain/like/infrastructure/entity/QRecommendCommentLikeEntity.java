package com.project.singk.domain.like.infrastructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecommendCommentLikeEntity is a Querydsl query type for RecommendCommentLikeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecommendCommentLikeEntity extends EntityPathBase<RecommendCommentLikeEntity> {

    private static final long serialVersionUID = -1257561474L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecommendCommentLikeEntity recommendCommentLikeEntity = new QRecommendCommentLikeEntity("recommendCommentLikeEntity");

    public final com.project.singk.domain.comment.infrastructure.entity.QRecommendCommentEntity comment;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.project.singk.domain.member.infrastructure.QMemberEntity member;

    public QRecommendCommentLikeEntity(String variable) {
        this(RecommendCommentLikeEntity.class, forVariable(variable), INITS);
    }

    public QRecommendCommentLikeEntity(Path<? extends RecommendCommentLikeEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecommendCommentLikeEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecommendCommentLikeEntity(PathMetadata metadata, PathInits inits) {
        this(RecommendCommentLikeEntity.class, metadata, inits);
    }

    public QRecommendCommentLikeEntity(Class<? extends RecommendCommentLikeEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.comment = inits.isInitialized("comment") ? new com.project.singk.domain.comment.infrastructure.entity.QRecommendCommentEntity(forProperty("comment"), inits.get("comment")) : null;
        this.member = inits.isInitialized("member") ? new com.project.singk.domain.member.infrastructure.QMemberEntity(forProperty("member"), inits.get("member")) : null;
    }

}

