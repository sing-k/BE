package com.project.singk.domain.post.service.port;

import com.project.singk.domain.post.domain.AlbumGenre;
import com.project.singk.domain.post.infrastructure.entity.AlbumGenreEntity;

public interface AlbumGenreRepository {
    AlbumGenre findGenreById(Long id);
    AlbumGenre findByGenre(AlbumGenre genre);
    Boolean existsByGenre(AlbumGenre genre);
    AlbumGenre save(AlbumGenre genre);
}
