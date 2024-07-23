package com.project.singk.domain.post.service.port;

import com.project.singk.domain.post.domain.AlbumGenre;
import com.project.singk.domain.post.domain.GenreType;

public interface AlbumGenreRepository {
    AlbumGenre findGenreById(Long id);
    AlbumGenre findByGenre(GenreType genre);
    Boolean existsByGenre(GenreType genre);
    AlbumGenre save(AlbumGenre genre);
}
