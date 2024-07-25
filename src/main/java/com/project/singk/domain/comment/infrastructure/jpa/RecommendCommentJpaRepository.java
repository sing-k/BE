package com.project.singk.domain.comment.infrastructure.jpa;

import com.project.singk.domain.comment.infrastructure.entity.RecommendCommentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface RecommendCommentJpaRepository extends JpaRepository<RecommendCommentEntity,Long> {
}
