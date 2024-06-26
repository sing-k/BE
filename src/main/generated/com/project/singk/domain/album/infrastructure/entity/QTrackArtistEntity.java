package com.project.singk.domain.album.infrastructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTrackArtistEntity is a Querydsl query type for TrackArtistEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTrackArtistEntity extends EntityPathBase<TrackArtistEntity> {

    private static final long serialVersionUID = 867183040L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTrackArtistEntity trackArtistEntity = new QTrackArtistEntity("trackArtistEntity");

    public final com.project.singk.global.domain.QBaseTimeEntity _super = new com.project.singk.global.domain.QBaseTimeEntity(this);

    public final QArtistEntity artist;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public QTrackArtistEntity(String variable) {
        this(TrackArtistEntity.class, forVariable(variable), INITS);
    }

    public QTrackArtistEntity(Path<? extends TrackArtistEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTrackArtistEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTrackArtistEntity(PathMetadata metadata, PathInits inits) {
        this(TrackArtistEntity.class, metadata, inits);
    }

    public QTrackArtistEntity(Class<? extends TrackArtistEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.artist = inits.isInitialized("artist") ? new QArtistEntity(forProperty("artist")) : null;
    }

}

