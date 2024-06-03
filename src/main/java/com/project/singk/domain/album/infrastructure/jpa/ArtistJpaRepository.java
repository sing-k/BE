package com.project.singk.domain.album.infrastructure.jpa;

import com.project.singk.domain.album.infrastructure.entity.ArtistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtistJpaRepository extends JpaRepository<ArtistEntity, String> {
    List<ArtistEntity> findByAlbumId(String albumId);
}
