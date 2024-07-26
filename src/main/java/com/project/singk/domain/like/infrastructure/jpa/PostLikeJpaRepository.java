package com.project.singk.domain.like.infrastructure.jpa;

import com.project.singk.domain.like.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeJpaRepository extends JpaRepository<PostLike,Long> {
}
