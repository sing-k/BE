package com.project.singk.domain.like.infrastructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFreePostLikeEntity is a Querydsl query type for FreePostLikeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFreePostLikeEntity extends EntityPathBase<FreePostLikeEntity> {

    private static final long serialVersionUID = 1629484903L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFreePostLikeEntity freePostLikeEntity = new QFreePostLikeEntity("freePostLikeEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.project.singk.domain.member.infrastructure.QMemberEntity member;

    public final com.project.singk.domain.post.infrastructure.entity.QFreePostEntity post;

    public QFreePostLikeEntity(String variable) {
        this(FreePostLikeEntity.class, forVariable(variable), INITS);
    }

    public QFreePostLikeEntity(Path<? extends FreePostLikeEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFreePostLikeEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFreePostLikeEntity(PathMetadata metadata, PathInits inits) {
        this(FreePostLikeEntity.class, metadata, inits);
    }

    public QFreePostLikeEntity(Class<? extends FreePostLikeEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.project.singk.domain.member.infrastructure.QMemberEntity(forProperty("member"), inits.get("member")) : null;
        this.post = inits.isInitialized("post") ? new com.project.singk.domain.post.infrastructure.entity.QFreePostEntity(forProperty("post"), inits.get("post")) : null;
    }

}

