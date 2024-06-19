package com.project.singk.domain.post.infrastructure.jpa;

import com.project.singk.domain.post.infrastructure.entity.key.GenreAlbumRecommendPostId;
import com.project.singk.domain.post.infrastructure.entity.GenreRecommendPostMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRecommendPostMappingJpaRepository
        extends JpaRepository<GenreRecommendPostMappingEntity, GenreAlbumRecommendPostId> {
}
