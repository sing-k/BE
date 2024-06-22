package com.project.singk.domain.album.infrastructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlbumArtistEntity is a Querydsl query type for AlbumArtistEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlbumArtistEntity extends EntityPathBase<AlbumArtistEntity> {

    private static final long serialVersionUID = 102965476L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAlbumArtistEntity albumArtistEntity = new QAlbumArtistEntity("albumArtistEntity");

    public final com.project.singk.global.domain.QBaseTimeEntity _super = new com.project.singk.global.domain.QBaseTimeEntity(this);

    public final QArtistEntity artist;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public QAlbumArtistEntity(String variable) {
        this(AlbumArtistEntity.class, forVariable(variable), INITS);
    }

    public QAlbumArtistEntity(Path<? extends AlbumArtistEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAlbumArtistEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAlbumArtistEntity(PathMetadata metadata, PathInits inits) {
        this(AlbumArtistEntity.class, metadata, inits);
    }

    public QAlbumArtistEntity(Class<? extends AlbumArtistEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.artist = inits.isInitialized("artist") ? new QArtistEntity(forProperty("artist")) : null;
    }

}

