package com.project.singk.domain.like.infrastructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecommendPostLikeEntity is a Querydsl query type for RecommendPostLikeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecommendPostLikeEntity extends EntityPathBase<RecommendPostLikeEntity> {

    private static final long serialVersionUID = -1125682987L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecommendPostLikeEntity recommendPostLikeEntity = new QRecommendPostLikeEntity("recommendPostLikeEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.project.singk.domain.member.infrastructure.QMemberEntity member;

    public final com.project.singk.domain.post.infrastructure.entity.QRecommendPostEntity post;

    public QRecommendPostLikeEntity(String variable) {
        this(RecommendPostLikeEntity.class, forVariable(variable), INITS);
    }

    public QRecommendPostLikeEntity(Path<? extends RecommendPostLikeEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecommendPostLikeEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecommendPostLikeEntity(PathMetadata metadata, PathInits inits) {
        this(RecommendPostLikeEntity.class, metadata, inits);
    }

    public QRecommendPostLikeEntity(Class<? extends RecommendPostLikeEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.project.singk.domain.member.infrastructure.QMemberEntity(forProperty("member"), inits.get("member")) : null;
        this.post = inits.isInitialized("post") ? new com.project.singk.domain.post.infrastructure.entity.QRecommendPostEntity(forProperty("post"), inits.get("post")) : null;
    }

}

