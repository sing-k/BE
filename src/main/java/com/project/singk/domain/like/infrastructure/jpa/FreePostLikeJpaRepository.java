package com.project.singk.domain.like.infrastructure.jpa;

import com.project.singk.domain.like.infrastructure.entity.FreePostLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreePostLikeJpaRepository extends JpaRepository<FreePostLikeEntity, Long> {
}
