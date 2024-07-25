package com.project.singk.domain.post.infrastructure.jpa;

import com.project.singk.domain.post.infrastructure.entity.PostEntity;
import com.project.singk.domain.post.infrastructure.entity.RecommendPostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendPostJpaRepository extends JpaRepository<RecommendPostEntity, Long> {
    Page<RecommendPostEntity> findAllByIsDeletedFalse(Pageable pageable);
    Page<RecommendPostEntity> findByMemberIdAndIsDeletedFalse(Long memberId, Pageable pageable);
}
