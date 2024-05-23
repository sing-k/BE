package com.project.singk.domain.album.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTrack is a Querydsl query type for Track
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTrack extends EntityPathBase<Track> {

    private static final long serialVersionUID = 562453526L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTrack track = new QTrack("track");

    public final com.project.singk.global.domain.QBaseTimeEntity _super = new com.project.singk.global.domain.QBaseTimeEntity(this);

    public final QAlbum album;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> duration = createNumber("duration", Long.class);

    public final StringPath id = createString("id");

    public final BooleanPath isPlayable = createBoolean("isPlayable");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final StringPath previewUrl = createString("previewUrl");

    public final NumberPath<Integer> trackNumber = createNumber("trackNumber", Integer.class);

    public QTrack(String variable) {
        this(Track.class, forVariable(variable), INITS);
    }

    public QTrack(Path<? extends Track> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTrack(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTrack(PathMetadata metadata, PathInits inits) {
        this(Track.class, metadata, inits);
    }

    public QTrack(Class<? extends Track> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.album = inits.isInitialized("album") ? new QAlbum(forProperty("album")) : null;
    }

}

