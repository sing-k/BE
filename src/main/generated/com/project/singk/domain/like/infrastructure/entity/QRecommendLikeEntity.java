package com.project.singk.domain.like.infrastructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecommendLikeEntity is a Querydsl query type for RecommendLikeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecommendLikeEntity extends EntityPathBase<RecommendLikeEntity> {

    private static final long serialVersionUID = -1325704811L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecommendLikeEntity recommendLikeEntity = new QRecommendLikeEntity("recommendLikeEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.project.singk.domain.member.infrastructure.QMemberEntity member;

    public final com.project.singk.domain.post.infrastructure.entity.QRecommendPostEntity post;

    public QRecommendLikeEntity(String variable) {
        this(RecommendLikeEntity.class, forVariable(variable), INITS);
    }

    public QRecommendLikeEntity(Path<? extends RecommendLikeEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecommendLikeEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecommendLikeEntity(PathMetadata metadata, PathInits inits) {
        this(RecommendLikeEntity.class, metadata, inits);
    }

    public QRecommendLikeEntity(Class<? extends RecommendLikeEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.project.singk.domain.member.infrastructure.QMemberEntity(forProperty("member"), inits.get("member")) : null;
        this.post = inits.isInitialized("post") ? new com.project.singk.domain.post.infrastructure.entity.QRecommendPostEntity(forProperty("post"), inits.get("post")) : null;
    }

}

