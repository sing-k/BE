package com.project.singk.domain.post.infrastructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFreePostEntity is a Querydsl query type for FreePostEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFreePostEntity extends EntityPathBase<FreePostEntity> {

    private static final long serialVersionUID = -1544785913L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFreePostEntity freePostEntity = new QFreePostEntity("freePostEntity");

    public final com.project.singk.global.domain.QBaseTimeEntity _super = new com.project.singk.global.domain.QBaseTimeEntity(this);

    public final NumberPath<Integer> comments = createNumber("comments", Integer.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> likes = createNumber("likes", Integer.class);

    public final com.project.singk.domain.member.infrastructure.QMemberEntity member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath title = createString("title");

    public QFreePostEntity(String variable) {
        this(FreePostEntity.class, forVariable(variable), INITS);
    }

    public QFreePostEntity(Path<? extends FreePostEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFreePostEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFreePostEntity(PathMetadata metadata, PathInits inits) {
        this(FreePostEntity.class, metadata, inits);
    }

    public QFreePostEntity(Class<? extends FreePostEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.project.singk.domain.member.infrastructure.QMemberEntity(forProperty("member"), inits.get("member")) : null;
    }

}

