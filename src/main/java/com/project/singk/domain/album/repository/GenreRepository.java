package com.project.singk.domain.album.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.singk.domain.album.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
	Optional<Genre> findByName(String name);
}
