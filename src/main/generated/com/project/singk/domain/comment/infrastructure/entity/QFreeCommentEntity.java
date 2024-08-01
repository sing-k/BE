package com.project.singk.domain.comment.infrastructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFreeCommentEntity is a Querydsl query type for FreeCommentEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFreeCommentEntity extends EntityPathBase<FreeCommentEntity> {

    private static final long serialVersionUID = -1216546735L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFreeCommentEntity freeCommentEntity = new QFreeCommentEntity("freeCommentEntity");

    public final com.project.singk.global.domain.QBaseTimeEntity _super = new com.project.singk.global.domain.QBaseTimeEntity(this);

    public final ListPath<FreeCommentEntity, QFreeCommentEntity> children = this.<FreeCommentEntity, QFreeCommentEntity>createList("children", FreeCommentEntity.class, QFreeCommentEntity.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> likes = createNumber("likes", Integer.class);

    public final com.project.singk.domain.member.infrastructure.QMemberEntity member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final QFreeCommentEntity parent;

    public final com.project.singk.domain.post.infrastructure.entity.QFreePostEntity post;

    public QFreeCommentEntity(String variable) {
        this(FreeCommentEntity.class, forVariable(variable), INITS);
    }

    public QFreeCommentEntity(Path<? extends FreeCommentEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFreeCommentEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFreeCommentEntity(PathMetadata metadata, PathInits inits) {
        this(FreeCommentEntity.class, metadata, inits);
    }

    public QFreeCommentEntity(Class<? extends FreeCommentEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.project.singk.domain.member.infrastructure.QMemberEntity(forProperty("member"), inits.get("member")) : null;
        this.parent = inits.isInitialized("parent") ? new QFreeCommentEntity(forProperty("parent"), inits.get("parent")) : null;
        this.post = inits.isInitialized("post") ? new com.project.singk.domain.post.infrastructure.entity.QFreePostEntity(forProperty("post"), inits.get("post")) : null;
    }

}

