package com.project.singk.domain.comment.infrastructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecommendCommentEntity is a Querydsl query type for RecommendCommentEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecommendCommentEntity extends EntityPathBase<RecommendCommentEntity> {

    private static final long serialVersionUID = 1575552107L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecommendCommentEntity recommendCommentEntity = new QRecommendCommentEntity("recommendCommentEntity");

    public final com.project.singk.global.domain.QBaseTimeEntity _super = new com.project.singk.global.domain.QBaseTimeEntity(this);

    public final ListPath<RecommendCommentEntity, QRecommendCommentEntity> children = this.<RecommendCommentEntity, QRecommendCommentEntity>createList("children", RecommendCommentEntity.class, QRecommendCommentEntity.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> likes = createNumber("likes", Integer.class);

    public final com.project.singk.domain.member.infrastructure.QMemberEntity member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final QRecommendCommentEntity parent;

    public final com.project.singk.domain.post.infrastructure.entity.QRecommendPostEntity post;

    public QRecommendCommentEntity(String variable) {
        this(RecommendCommentEntity.class, forVariable(variable), INITS);
    }

    public QRecommendCommentEntity(Path<? extends RecommendCommentEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecommendCommentEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecommendCommentEntity(PathMetadata metadata, PathInits inits) {
        this(RecommendCommentEntity.class, metadata, inits);
    }

    public QRecommendCommentEntity(Class<? extends RecommendCommentEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.project.singk.domain.member.infrastructure.QMemberEntity(forProperty("member"), inits.get("member")) : null;
        this.parent = inits.isInitialized("parent") ? new QRecommendCommentEntity(forProperty("parent"), inits.get("parent")) : null;
        this.post = inits.isInitialized("post") ? new com.project.singk.domain.post.infrastructure.entity.QRecommendPostEntity(forProperty("post"), inits.get("post")) : null;
    }

}

