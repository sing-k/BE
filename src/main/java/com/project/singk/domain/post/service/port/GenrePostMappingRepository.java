package com.project.singk.domain.post.service.port;

import com.project.singk.domain.post.domain.GenrePostMapping;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface GenrePostMappingRepository {
    List<GenrePostMapping> findByGenre(Long genreId);
}
