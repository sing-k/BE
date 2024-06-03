package com.project.singk.domain.album.infrastructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTrackEntity is a Querydsl query type for TrackEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTrackEntity extends EntityPathBase<TrackEntity> {

    private static final long serialVersionUID = 2060038169L;

    public static final QTrackEntity trackEntity = new QTrackEntity("trackEntity");

    public final com.project.singk.global.domain.QBaseTimeEntity _super = new com.project.singk.global.domain.QBaseTimeEntity(this);

    public final StringPath albumId = createString("albumId");

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

    public QTrackEntity(String variable) {
        super(TrackEntity.class, forVariable(variable));
    }

    public QTrackEntity(Path<? extends TrackEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTrackEntity(PathMetadata metadata) {
        super(TrackEntity.class, metadata);
    }

}

