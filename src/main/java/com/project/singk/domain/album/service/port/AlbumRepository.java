package com.project.singk.domain.album.service.port;

import java.util.Optional;

import com.project.singk.domain.album.domain.Album;

public interface AlbumRepository {
	Album save(Album album);
	Album getById(String id);
	Optional<Album> findById(String id);
}
