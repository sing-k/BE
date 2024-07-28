package com.project.singk.domain.album.service.port;

import java.util.List;
import java.util.Optional;

import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.album.domain.AlbumSimplified;
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
    Page<AlbumSimplified> findAllByModifiedAt(String cursorId, String cursorDate, int limit);
    Page<AlbumSimplified> findAllByAverageScore(String cursorId, String cursorScore, int limit);
    Page<AlbumSimplified> findAllByReviewCount(String cursorId, String cursorReviewCount, int limit);
}
