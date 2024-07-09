package com.project.singk.domain.post.service.port;

import com.project.singk.domain.post.domain.GenrePostMapping;

import java.util.List;

public interface GenrePostMappingRepository {
    List<GenrePostMapping> findByGenre(Long genreId);
}
