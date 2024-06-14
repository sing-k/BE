package com.project.singk.domain.album.service.port;

import java.util.List;
import java.util.Optional;

import com.project.singk.domain.album.controller.request.AlbumSort;
import com.project.singk.domain.album.domain.Album;
import org.springframework.data.domain.Page;

public interface AlbumRepository {
	Album save(Album album);
    List<Album> saveAll(List<Album> albums);
	Album getById(String id);
	Optional<Album> findById(String id);
    Page<Album> findAllByAlbumSort(AlbumSort sort, int offset, int limit);
    Page<Album> findAllByModifiedAt(String cursorId, String cursorDate, int limit);
    Page<Album> findAllByAverageScore(String cursorId, String cursorScore, int limit);
    Page<Album> findAllByReviewCount(String cursorId, String cursorReviewCount, int limit);
}
