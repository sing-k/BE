package com.project.singk.domain.album.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlbumImage is a Querydsl query type for AlbumImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlbumImage extends EntityPathBase<AlbumImage> {

    private static final long serialVersionUID = -1841134527L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAlbumImage albumImage = new QAlbumImage("albumImage");

    public final com.project.singk.global.domain.QBaseTimeEntity _super = new com.project.singk.global.domain.QBaseTimeEntity(this);

    public final QAlbum album;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Integer> height = createNumber("height", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Integer> width = createNumber("width", Integer.class);

    public QAlbumImage(String variable) {
        this(AlbumImage.class, forVariable(variable), INITS);
    }

    public QAlbumImage(Path<? extends AlbumImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAlbumImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAlbumImage(PathMetadata metadata, PathInits inits) {
        this(AlbumImage.class, metadata, inits);
    }

    public QAlbumImage(Class<? extends AlbumImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.album = inits.isInitialized("album") ? new QAlbum(forProperty("album")) : null;
    }

}

