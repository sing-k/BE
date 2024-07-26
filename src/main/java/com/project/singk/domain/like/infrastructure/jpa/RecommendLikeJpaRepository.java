package com.project.singk.domain.like.infrastructure.jpa;

import com.project.singk.domain.like.domain.RecommendLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendLikeJpaRepository extends JpaRepository<RecommendLike,Long> {
}
