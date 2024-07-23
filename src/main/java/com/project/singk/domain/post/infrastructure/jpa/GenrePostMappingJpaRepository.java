package com.project.singk.domain.post.infrastructure.jpa;

import com.project.singk.domain.post.infrastructure.entity.key.GenrePostId;
import com.project.singk.domain.post.infrastructure.entity.GenrePostMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenrePostMappingJpaRepository
        extends JpaRepository<GenrePostMappingEntity, GenrePostId> {
}
