package com.project.singk.domain.album.infrastructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAlbumGenreEntity is a Querydsl query type for AlbumGenreEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlbumGenreEntity extends EntityPathBase<AlbumGenreEntity> {

    private static final long serialVersionUID = -1938004692L;

    public static final QAlbumGenreEntity albumGenreEntity = new QAlbumGenreEntity("albumGenreEntity");

    public final EnumPath<com.project.singk.domain.album.domain.GenreType> genre = createEnum("genre", com.project.singk.domain.album.domain.GenreType.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QAlbumGenreEntity(String variable) {
        super(AlbumGenreEntity.class, forVariable(variable));
    }

    public QAlbumGenreEntity(Path<? extends AlbumGenreEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAlbumGenreEntity(PathMetadata metadata) {
        super(AlbumGenreEntity.class, metadata);
    }

}

