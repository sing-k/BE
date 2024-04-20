package com.project.singk.domain.album.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.singk.domain.album.domain.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {
	Optional<Album> findByName(String name);
}
