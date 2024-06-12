package com.project.singk.domain.album.service.port;

import java.util.Optional;

import com.project.singk.domain.album.controller.request.AlbumSort;
import com.project.singk.domain.album.domain.Album;
import com.project.singk.global.api.Page;
import org.springframework.data.domain.Pageable;

public interface AlbumRepository {
	Album save(Album album);
	Album getById(String id);
	Optional<Album> findById(String id);
    Page<Album> findAllByAlbumSort(AlbumSort sort, int offset, int limit);
}
