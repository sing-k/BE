package com.project.singk.domain.vote.infrastructure;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlbumReviewVoteEntity is a Querydsl query type for AlbumReviewVoteEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlbumReviewVoteEntity extends EntityPathBase<AlbumReviewVoteEntity> {

    private static final long serialVersionUID = 1683896215L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAlbumReviewVoteEntity albumReviewVoteEntity = new QAlbumReviewVoteEntity("albumReviewVoteEntity");

    public final com.project.singk.global.domain.QBaseTimeEntity _super = new com.project.singk.global.domain.QBaseTimeEntity(this);

    public final com.project.singk.domain.review.infrastructure.QAlbumReviewEntity albumReview;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.project.singk.domain.member.infrastructure.QMemberEntity member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final EnumPath<com.project.singk.domain.vote.domain.VoteType> type = createEnum("type", com.project.singk.domain.vote.domain.VoteType.class);

    public QAlbumReviewVoteEntity(String variable) {
        this(AlbumReviewVoteEntity.class, forVariable(variable), INITS);
    }

    public QAlbumReviewVoteEntity(Path<? extends AlbumReviewVoteEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAlbumReviewVoteEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAlbumReviewVoteEntity(PathMetadata metadata, PathInits inits) {
        this(AlbumReviewVoteEntity.class, metadata, inits);
    }

    public QAlbumReviewVoteEntity(Class<? extends AlbumReviewVoteEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.albumReview = inits.isInitialized("albumReview") ? new com.project.singk.domain.review.infrastructure.QAlbumReviewEntity(forProperty("albumReview"), inits.get("albumReview")) : null;
        this.member = inits.isInitialized("member") ? new com.project.singk.domain.member.infrastructure.QMemberEntity(forProperty("member"), inits.get("member")) : null;
    }

}

