package com.project.singk.domain.like.infrastructure.jpa;

import com.project.singk.domain.like.domain.RecommendCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendCommentLikeJpaRepository extends JpaRepository<RecommendCommentLike,Long> {
}
