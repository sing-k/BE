package com.project.singk.domain.vote.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlbumReviewVote is a Querydsl query type for AlbumReviewVote
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlbumReviewVote extends EntityPathBase<AlbumReviewVote> {

    private static final long serialVersionUID = -1422212683L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAlbumReviewVote albumReviewVote = new QAlbumReviewVote("albumReviewVote");

    public final com.project.singk.global.domain.QBaseTimeEntity _super = new com.project.singk.global.domain.QBaseTimeEntity(this);

    public final com.project.singk.domain.review.domain.QAlbumReview albumReview;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.project.singk.domain.member.domain.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final EnumPath<VoteType> type = createEnum("type", VoteType.class);

    public QAlbumReviewVote(String variable) {
        this(AlbumReviewVote.class, forVariable(variable), INITS);
    }

    public QAlbumReviewVote(Path<? extends AlbumReviewVote> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAlbumReviewVote(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAlbumReviewVote(PathMetadata metadata, PathInits inits) {
        this(AlbumReviewVote.class, metadata, inits);
    }

    public QAlbumReviewVote(Class<? extends AlbumReviewVote> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.albumReview = inits.isInitialized("albumReview") ? new com.project.singk.domain.review.domain.QAlbumReview(forProperty("albumReview"), inits.get("albumReview")) : null;
        this.member = inits.isInitialized("member") ? new com.project.singk.domain.member.domain.QMember(forProperty("member")) : null;
    }

}

