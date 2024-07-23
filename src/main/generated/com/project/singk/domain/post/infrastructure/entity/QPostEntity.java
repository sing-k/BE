package com.project.singk.domain.post.infrastructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostEntity is a Querydsl query type for PostEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostEntity extends EntityPathBase<PostEntity> {

    private static final long serialVersionUID = 699285627L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostEntity postEntity = new QPostEntity("postEntity");

    public final com.project.singk.global.domain.QBaseTimeEntity _super = new com.project.singk.global.domain.QBaseTimeEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final NumberPath<Integer> likes = createNumber("likes", Integer.class);

    public final com.project.singk.domain.member.infrastructure.QMemberEntity member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final EnumPath<com.project.singk.domain.post.domain.PostType> postType = createEnum("postType", com.project.singk.domain.post.domain.PostType.class);

    public final StringPath thumbnailUrl = createString("thumbnailUrl");

    public final StringPath title = createString("title");

    public QPostEntity(String variable) {
        this(PostEntity.class, forVariable(variable), INITS);
    }

    public QPostEntity(Path<? extends PostEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostEntity(PathMetadata metadata, PathInits inits) {
        this(PostEntity.class, metadata, inits);
    }

    public QPostEntity(Class<? extends PostEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.project.singk.domain.member.infrastructure.QMemberEntity(forProperty("member")) : null;
    }

}

