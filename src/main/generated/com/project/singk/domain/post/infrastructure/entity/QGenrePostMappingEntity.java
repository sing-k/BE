package com.project.singk.domain.post.infrastructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGenrePostMappingEntity is a Querydsl query type for GenrePostMappingEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGenrePostMappingEntity extends EntityPathBase<GenrePostMappingEntity> {

    private static final long serialVersionUID = 1972767270L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGenrePostMappingEntity genrePostMappingEntity = new QGenrePostMappingEntity("genrePostMappingEntity");

    public final com.project.singk.domain.post.infrastructure.entity.key.QGenrePostId genrePostId;

    public QGenrePostMappingEntity(String variable) {
        this(GenrePostMappingEntity.class, forVariable(variable), INITS);
    }

    public QGenrePostMappingEntity(Path<? extends GenrePostMappingEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGenrePostMappingEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGenrePostMappingEntity(PathMetadata metadata, PathInits inits) {
        this(GenrePostMappingEntity.class, metadata, inits);
    }

    public QGenrePostMappingEntity(Class<? extends GenrePostMappingEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.genrePostId = inits.isInitialized("genrePostId") ? new com.project.singk.domain.post.infrastructure.entity.key.QGenrePostId(forProperty("genrePostId")) : null;
    }

}

