package com.project.singk.domain.post.infrastructure.jpa;

import com.project.singk.domain.post.infrastructure.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<PostEntity, Long> {
}
