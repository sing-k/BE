package com.project.singk.domain.post.infrastructure.jpa;

import com.project.singk.domain.post.infrastructure.entity.RecommendPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendPostJpaRepository extends JpaRepository<RecommendPostEntity, Long> {
}
