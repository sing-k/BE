package com.project.singk.domain.album.service.port;

import com.project.singk.domain.album.infrastructure.spotify.AlbumEntity;
import com.project.singk.domain.album.infrastructure.spotify.AlbumSimplifiedEntity;
import com.project.singk.global.api.Page;

public interface SpotifyRepository {
	AlbumEntity getAlbumById(String id);
	Page<AlbumSimplifiedEntity> searchAlbums(String query, int offset, int limit);
}
