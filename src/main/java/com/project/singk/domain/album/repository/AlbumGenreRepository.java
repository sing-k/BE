package com.project.singk.domain.album.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.singk.domain.album.domain.AlbumGenre;

public interface AlbumGenreRepository extends JpaRepository<AlbumGenre, Long> {
}
