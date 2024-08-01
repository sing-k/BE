package com.project.singk.domain.post.infrastructure.jpa;

import com.project.singk.domain.post.infrastructure.entity.FreePostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreePostJpaRepository extends JpaRepository<FreePostEntity, Long> {
}
