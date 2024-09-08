package com.project.singk.domain.album.service.port;

import com.project.singk.domain.album.infrastructure.spotify.AlbumEntity;
import com.project.singk.domain.album.infrastructure.spotify.AlbumSimplifiedEntity;
import com.project.singk.global.api.OffsetPageResponse;

import java.util.concurrent.CompletableFuture;

public interface SpotifyRepository {
	AlbumEntity getAlbumById(String id);
    CompletableFuture<AlbumEntity> getAlbumByIdWithAsync(String id);
	OffsetPageResponse<AlbumSimplifiedEntity> searchAlbums(String query, int offset, int limit);
}
