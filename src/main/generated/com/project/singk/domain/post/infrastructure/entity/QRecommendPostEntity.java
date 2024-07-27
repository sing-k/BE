package com.project.singk.domain.post.infrastructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecommendPostEntity is a Querydsl query type for RecommendPostEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecommendPostEntity extends EntityPathBase<RecommendPostEntity> {

    private static final long serialVersionUID = 1612588455L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecommendPostEntity recommendPostEntity = new QRecommendPostEntity("recommendPostEntity");

    public final com.project.singk.global.domain.QBaseTimeEntity _super = new com.project.singk.global.domain.QBaseTimeEntity(this);

    public final NumberPath<Integer> comments = createNumber("comments", Integer.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final EnumPath<com.project.singk.domain.album.domain.GenreType> genre = createEnum("genre", com.project.singk.domain.album.domain.GenreType.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> likes = createNumber("likes", Integer.class);

    public final StringPath link = createString("link");

    public final com.project.singk.domain.member.infrastructure.QMemberEntity member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final EnumPath<com.project.singk.domain.post.domain.RecommendType> recommend = createEnum("recommend", com.project.singk.domain.post.domain.RecommendType.class);

    public final StringPath title = createString("title");

    public QRecommendPostEntity(String variable) {
        this(RecommendPostEntity.class, forVariable(variable), INITS);
    }

    public QRecommendPostEntity(Path<? extends RecommendPostEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecommendPostEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecommendPostEntity(PathMetadata metadata, PathInits inits) {
        this(RecommendPostEntity.class, metadata, inits);
    }

    public QRecommendPostEntity(Class<? extends RecommendPostEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.project.singk.domain.member.infrastructure.QMemberEntity(forProperty("member"), inits.get("member")) : null;
    }

}

