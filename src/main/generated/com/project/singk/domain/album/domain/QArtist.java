package com.project.singk.domain.album.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArtist is a Querydsl query type for Artist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArtist extends EntityPathBase<Artist> {

    private static final long serialVersionUID = -287191588L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QArtist artist = new QArtist("artist");

    public final com.project.singk.global.domain.QBaseTimeEntity _super = new com.project.singk.global.domain.QBaseTimeEntity(this);

    public final QAlbum album;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath id = createString("id");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public QArtist(String variable) {
        this(Artist.class, forVariable(variable), INITS);
    }

    public QArtist(Path<? extends Artist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QArtist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QArtist(PathMetadata metadata, PathInits inits) {
        this(Artist.class, metadata, inits);
    }

    public QArtist(Class<? extends Artist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.album = inits.isInitialized("album") ? new QAlbum(forProperty("album")) : null;
    }

}

