package com.project.singk.domain.album.infrastructure.jpa;

import com.project.singk.domain.album.infrastructure.entity.TrackEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrackJpaRepository extends JpaRepository<TrackEntity, String> {
    List<TrackEntity> findByAlbumId(String albumId);
}
