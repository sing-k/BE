package com.project.singk.domain.like.infrastructure.jpa;

import com.project.singk.domain.like.domain.PostCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentLikeJpaRepository extends JpaRepository<PostCommentLike,Long> {
}
