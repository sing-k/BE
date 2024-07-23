package com.project.singk.domain.post.infrastructure.entity.key;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGenrePostId is a Querydsl query type for GenrePostId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QGenrePostId extends BeanPath<GenrePostId> {

    private static final long serialVersionUID = 1182369623L;

    public static final QGenrePostId genrePostId = new QGenrePostId("genrePostId");

    public final NumberPath<Long> genreId = createNumber("genreId", Long.class);

    public final NumberPath<Long> postId = createNumber("postId", Long.class);

    public QGenrePostId(String variable) {
        super(GenrePostId.class, forVariable(variable));
    }

    public QGenrePostId(Path<? extends GenrePostId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGenrePostId(PathMetadata metadata) {
        super(GenrePostId.class, metadata);
    }

}

