package com.project.singk.domain.album.infrastructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QArtistEntity is a Querydsl query type for ArtistEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArtistEntity extends EntityPathBase<ArtistEntity> {

    private static final long serialVersionUID = -1772950433L;

    public static final QArtistEntity artistEntity = new QArtistEntity("artistEntity");

    public final com.project.singk.global.domain.QBaseTimeEntity _super = new com.project.singk.global.domain.QBaseTimeEntity(this);

    public final StringPath albumId = createString("albumId");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath id = createString("id");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public QArtistEntity(String variable) {
        super(ArtistEntity.class, forVariable(variable));
    }

    public QArtistEntity(Path<? extends ArtistEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QArtistEntity(PathMetadata metadata) {
        super(ArtistEntity.class, metadata);
    }

}

