package com.project.singk.domain.comment.infrastructure.jpa;

import com.project.singk.domain.comment.infrastructure.entity.RecommendCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendCommentJpaRepository extends JpaRepository<RecommendCommentEntity, Long> {
}
