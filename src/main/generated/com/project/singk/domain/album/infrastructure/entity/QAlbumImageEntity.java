package com.project.singk.domain.album.infrastructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAlbumImageEntity is a Querydsl query type for AlbumImageEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlbumImageEntity extends EntityPathBase<AlbumImageEntity> {

    private static final long serialVersionUID = 1516946180L;

    public static final QAlbumImageEntity albumImageEntity = new QAlbumImageEntity("albumImageEntity");

    public final com.project.singk.global.domain.QBaseTimeEntity _super = new com.project.singk.global.domain.QBaseTimeEntity(this);

    public final StringPath albumId = createString("albumId");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Integer> height = createNumber("height", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Integer> width = createNumber("width", Integer.class);

    public QAlbumImageEntity(String variable) {
        super(AlbumImageEntity.class, forVariable(variable));
    }

    public QAlbumImageEntity(Path<? extends AlbumImageEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAlbumImageEntity(PathMetadata metadata) {
        super(AlbumImageEntity.class, metadata);
    }

}

