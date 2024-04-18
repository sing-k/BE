package com.project.singk.domain.album.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.singk.domain.album.domain.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {

}
