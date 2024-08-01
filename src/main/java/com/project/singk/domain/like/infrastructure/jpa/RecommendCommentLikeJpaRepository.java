package com.project.singk.domain.like.infrastructure.jpa;

import com.project.singk.domain.like.infrastructure.entity.RecommendCommentLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendCommentLikeJpaRepository extends JpaRepository<RecommendCommentLikeEntity, Long> {
}
