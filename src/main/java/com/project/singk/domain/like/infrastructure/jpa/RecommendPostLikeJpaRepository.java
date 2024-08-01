package com.project.singk.domain.like.infrastructure.jpa;

import com.project.singk.domain.like.infrastructure.entity.RecommendPostLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendPostLikeJpaRepository extends JpaRepository<RecommendPostLikeEntity, Long> {
}
