package com.project.singk.domain.comment.infrastructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostCommentEntity is a Querydsl query type for PostCommentEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostCommentEntity extends EntityPathBase<PostCommentEntity> {

    private static final long serialVersionUID = 1600694941L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostCommentEntity postCommentEntity = new QPostCommentEntity("postCommentEntity");

    public final com.project.singk.global.domain.QBaseTimeEntity _super = new com.project.singk.global.domain.QBaseTimeEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final com.project.singk.domain.member.infrastructure.QMemberEntity member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final QPostCommentEntity parent;

    public final com.project.singk.domain.post.infrastructure.entity.QPostEntity post;

    public QPostCommentEntity(String variable) {
        this(PostCommentEntity.class, forVariable(variable), INITS);
    }

    public QPostCommentEntity(Path<? extends PostCommentEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostCommentEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostCommentEntity(PathMetadata metadata, PathInits inits) {
        this(PostCommentEntity.class, metadata, inits);
    }

    public QPostCommentEntity(Class<? extends PostCommentEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.project.singk.domain.member.infrastructure.QMemberEntity(forProperty("member"), inits.get("member")) : null;
        this.parent = inits.isInitialized("parent") ? new QPostCommentEntity(forProperty("parent"), inits.get("parent")) : null;
        this.post = inits.isInitialized("post") ? new com.project.singk.domain.post.infrastructure.entity.QPostEntity(forProperty("post"), inits.get("post")) : null;
    }

}

