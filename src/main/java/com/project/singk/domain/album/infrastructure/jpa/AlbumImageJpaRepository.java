package com.project.singk.domain.album.infrastructure.jpa;

import com.project.singk.domain.album.infrastructure.entity.AlbumImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumImageJpaRepository extends JpaRepository<AlbumImageEntity, String> {
    List<AlbumImageEntity> findByAlbumId(String albumId);
}
