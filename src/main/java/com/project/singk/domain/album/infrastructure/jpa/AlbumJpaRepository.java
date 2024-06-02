package com.project.singk.domain.album.infrastructure.jpa;

import com.project.singk.domain.album.infrastructure.entity.AlbumEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumJpaRepository extends JpaRepository<AlbumEntity, String> {
}
