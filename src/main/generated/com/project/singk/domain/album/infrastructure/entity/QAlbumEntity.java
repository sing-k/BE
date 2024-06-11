package com.project.singk.domain.album.infrastructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlbumEntity is a Querydsl query type for AlbumEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlbumEntity extends EntityPathBase<AlbumEntity> {

    private static final long serialVersionUID = -142787L;

    public static final QAlbumEntity albumEntity = new QAlbumEntity("albumEntity");

    public final com.project.singk.global.domain.QBaseTimeEntity _super = new com.project.singk.global.domain.QBaseTimeEntity(this);

    public final ListPath<ArtistEntity, QArtistEntity> artists = this.<ArtistEntity, QArtistEntity>createList("artists", ArtistEntity.class, QArtistEntity.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath id = createString("id");

    public final ListPath<AlbumImageEntity, QAlbumImageEntity> images = this.<AlbumImageEntity, QAlbumImageEntity>createList("images", AlbumImageEntity.class, QAlbumImageEntity.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final DateTimePath<java.time.LocalDateTime> releasedAt = createDateTime("releasedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> totalReviewer = createNumber("totalReviewer", Long.class);

    public final NumberPath<Long> totalScore = createNumber("totalScore", Long.class);

    public final ListPath<TrackEntity, QTrackEntity> tracks = this.<TrackEntity, QTrackEntity>createList("tracks", TrackEntity.class, QTrackEntity.class, PathInits.DIRECT2);

    public final EnumPath<com.project.singk.domain.album.domain.AlbumType> type = createEnum("type", com.project.singk.domain.album.domain.AlbumType.class);

    public QAlbumEntity(String variable) {
        super(AlbumEntity.class, forVariable(variable));
    }

    public QAlbumEntity(Path<? extends AlbumEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAlbumEntity(PathMetadata metadata) {
        super(AlbumEntity.class, metadata);
    }

}

