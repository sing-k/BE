package com.project.singk.domain.album.service.port;

import java.util.List;
import java.util.Optional;

import com.project.singk.domain.album.controller.request.AlbumSort;
import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.review.domain.AlbumReviewStatistics;
import org.springframework.data.domain.Page;

public interface AlbumRepository {
	Album save(Album album);
    List<Album> saveAll(List<Album> albums);
    boolean existsById(String albumId);
	Album getById(String albumId);
    Album getByIdWithStatistics(String albumId);
    AlbumReviewStatistics getAlbumReviewStatisticsByAlbumId(String albumId);
    Optional<Album> findByIdWithStatistics(String albumId);
	Optional<Album> findById(String albumId);
    Page<Album> findAllByModifiedAt(String cursorId, String cursorDate, int limit);
    Page<Album> findAllByAverageScore(String cursorId, String cursorScore, int limit);
    Page<Album> findAllByReviewCount(String cursorId, String cursorReviewCount, int limit);
}
