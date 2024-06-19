package com.project.singk.domain.post.infrastructure.jpa;

import com.project.singk.domain.post.infrastructure.entity.AlbumGenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumGenreJpaRepository extends JpaRepository<AlbumGenreEntity, Long> {
}
