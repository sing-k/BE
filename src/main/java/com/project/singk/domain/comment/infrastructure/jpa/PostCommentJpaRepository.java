package com.project.singk.domain.comment.infrastructure.jpa;

import com.project.singk.domain.comment.infrastructure.entity.PostCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentJpaRepository extends JpaRepository<PostCommentEntity,Long> {
}
