package com.project.singk.domain.album.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlbum is a Querydsl query type for Album
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlbum extends EntityPathBase<Album> {

    private static final long serialVersionUID = 544729402L;

    public static final QAlbum album = new QAlbum("album");

    public final com.project.singk.global.domain.QBaseTimeEntity _super = new com.project.singk.global.domain.QBaseTimeEntity(this);

    public final ListPath<Artist, QArtist> artists = this.<Artist, QArtist>createList("artists", Artist.class, QArtist.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath id = createString("id");

    public final ListPath<AlbumImage, QAlbumImage> images = this.<AlbumImage, QAlbumImage>createList("images", AlbumImage.class, QAlbumImage.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final DateTimePath<java.time.LocalDateTime> releasedAt = createDateTime("releasedAt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> trackCount = createNumber("trackCount", Integer.class);

    public final ListPath<Track, QTrack> tracks = this.<Track, QTrack>createList("tracks", Track.class, QTrack.class, PathInits.DIRECT2);

    public final EnumPath<AlbumType> type = createEnum("type", AlbumType.class);

    public QAlbum(String variable) {
        super(Album.class, forVariable(variable));
    }

    public QAlbum(Path<? extends Album> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAlbum(PathMetadata metadata) {
        super(Album.class, metadata);
    }

}

