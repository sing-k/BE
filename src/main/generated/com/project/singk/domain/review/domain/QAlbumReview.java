package com.project.singk.domain.review.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlbumReview is a Querydsl query type for AlbumReview
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlbumReview extends EntityPathBase<AlbumReview> {

    private static final long serialVersionUID = 1411618429L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAlbumReview albumReview = new QAlbumReview("albumReview");

    public final com.project.singk.global.domain.QBaseTimeEntity _super = new com.project.singk.global.domain.QBaseTimeEntity(this);

    public final com.project.singk.domain.album.domain.QAlbum album;

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

    public QAlbumReview(String variable) {
        this(AlbumReview.class, forVariable(variable), INITS);
    }

    public QAlbumReview(Path<? extends AlbumReview> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAlbumReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAlbumReview(PathMetadata metadata, PathInits inits) {
        this(AlbumReview.class, metadata, inits);
    }

    public QAlbumReview(Class<? extends AlbumReview> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.album = inits.isInitialized("album") ? new com.project.singk.domain.album.domain.QAlbum(forProperty("album")) : null;
        this.member = inits.isInitialized("member") ? new com.project.singk.domain.member.infrastructure.QMemberEntity(forProperty("member")) : null;
    }

}

