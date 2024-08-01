package com.project.singk.domain.like.infrastructure.jpa;

import com.project.singk.domain.like.infrastructure.entity.FreeCommentLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreeCommentLikeJpaRepository extends JpaRepository<FreeCommentLikeEntity, Long> {
}
