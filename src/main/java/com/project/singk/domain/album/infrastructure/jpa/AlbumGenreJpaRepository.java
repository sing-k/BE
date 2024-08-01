package com.project.singk.domain.album.infrastructure.jpa;

import com.project.singk.domain.album.domain.GenreType;
import com.project.singk.domain.album.infrastructure.entity.AlbumGenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlbumGenreJpaRepository extends JpaRepository<AlbumGenreEntity, Long> {
    Optional<AlbumGenreEntity> findByGenre(GenreType genre);
    Boolean existsByGenre(GenreType genre);
}
