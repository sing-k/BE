package com.project.singk.domain.album.service.port;

import com.project.singk.domain.album.infrastructure.spotify.AlbumEntity;
import com.project.singk.domain.album.infrastructure.spotify.AlbumSimplifiedEntity;
import com.project.singk.global.api.PageResponse;

public interface SpotifyRepository {
	AlbumEntity getAlbumById(String id);
	PageResponse<AlbumSimplifiedEntity> searchAlbums(String query, int offset, int limit);
}
