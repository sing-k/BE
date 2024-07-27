package com.project.singk.domain.like.infrastructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostLikeEntity is a Querydsl query type for PostLikeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostLikeEntity extends EntityPathBase<PostLikeEntity> {

    private static final long serialVersionUID = -204453285L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostLikeEntity postLikeEntity = new QPostLikeEntity("postLikeEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.project.singk.domain.member.infrastructure.QMemberEntity member;

    public final com.project.singk.domain.post.infrastructure.entity.QPostEntity post;

    public QPostLikeEntity(String variable) {
        this(PostLikeEntity.class, forVariable(variable), INITS);
    }

    public QPostLikeEntity(Path<? extends PostLikeEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostLikeEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostLikeEntity(PathMetadata metadata, PathInits inits) {
        this(PostLikeEntity.class, metadata, inits);
    }

    public QPostLikeEntity(Class<? extends PostLikeEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.project.singk.domain.member.infrastructure.QMemberEntity(forProperty("member"), inits.get("member")) : null;
        this.post = inits.isInitialized("post") ? new com.project.singk.domain.post.infrastructure.entity.QPostEntity(forProperty("post"), inits.get("post")) : null;
    }

}

