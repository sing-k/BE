package com.project.singk.domain.album.infrastructure;

import com.project.singk.domain.album.domain.AlbumGenre;
import com.project.singk.domain.album.domain.GenreType;
import com.project.singk.domain.album.infrastructure.entity.AlbumGenreEntity;
import com.project.singk.domain.album.infrastructure.jpa.AlbumGenreJpaRepository;
import com.project.singk.domain.album.service.port.AlbumGenreRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class AlbumGenreRepositoryImpl implements AlbumGenreRepository {

    private final AlbumGenreJpaRepository albumGenreJpaRepository;

    @Override
    public AlbumGenre findGenreById(Long id) {
        return albumGenreJpaRepository.findById(id).orElseThrow(
                () -> new ApiException(AppHttpStatus.NOT_FOUND_ALBUM_GENRE))
                .toModel();
    }

    @Override
    public AlbumGenre findByGenre(GenreType genre) {
        return albumGenreJpaRepository.findByGenre(genre).orElseThrow(
                        () -> new ApiException(AppHttpStatus.NOT_FOUND_ALBUM_GENRE))
                .toModel();
    }

    @Override
    public Boolean existsByGenre(GenreType genre) {
        return albumGenreJpaRepository.existsByGenre(genre);
    }

    @Override
    public AlbumGenre save(AlbumGenre albumGenre) {
        return albumGenreJpaRepository.save(AlbumGenreEntity.from(albumGenre)).toModel();
    }
}
