package com.project.singk.domain.comment.infrastructure.jpa;

import com.project.singk.domain.comment.infrastructure.entity.FreeCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreeCommentJpaRepository extends JpaRepository<FreeCommentEntity, Long> {
}
