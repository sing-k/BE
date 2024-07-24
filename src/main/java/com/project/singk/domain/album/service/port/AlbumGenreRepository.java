package com.project.singk.domain.album.service.port;

import com.project.singk.domain.album.domain.AlbumGenre;
import com.project.singk.domain.album.domain.GenreType;

public interface AlbumGenreRepository {
    AlbumGenre findGenreById(Long id);
    AlbumGenre findByGenre(GenreType genre);
    Boolean existsByGenre(GenreType genre);
    AlbumGenre save(AlbumGenre genre);
}
