package com.project.singk.domain.review.infrastructure;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlbumReviewEntity is a Querydsl query type for AlbumReviewEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlbumReviewEntity extends EntityPathBase<AlbumReviewEntity> {

    private static final long serialVersionUID = 1457064927L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAlbumReviewEntity albumReviewEntity = new QAlbumReviewEntity("albumReviewEntity");

    public final com.project.singk.global.domain.QBaseTimeEntity _super = new com.project.singk.global.domain.QBaseTimeEntity(this);

    public final com.project.singk.domain.album.infrastructure.entity.QAlbumEntity album;

    public final NumberPath<Integer> consCount = createNumber("consCount", Integer.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.project.singk.domain.member.infrastructure.QMemberEntity member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Integer> prosCount = createNumber("prosCount", Integer.class);

    public final NumberPath<Integer> score = createNumber("score", Integer.class);

    public QAlbumReviewEntity(String variable) {
        this(AlbumReviewEntity.class, forVariable(variable), INITS);
    }

    public QAlbumReviewEntity(Path<? extends AlbumReviewEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAlbumReviewEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAlbumReviewEntity(PathMetadata metadata, PathInits inits) {
        this(AlbumReviewEntity.class, metadata, inits);
    }

    public QAlbumReviewEntity(Class<? extends AlbumReviewEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.album = inits.isInitialized("album") ? new com.project.singk.domain.album.infrastructure.entity.QAlbumEntity(forProperty("album"), inits.get("album")) : null;
        this.member = inits.isInitialized("member") ? new com.project.singk.domain.member.infrastructure.QMemberEntity(forProperty("member"), inits.get("member")) : null;
    }

}

