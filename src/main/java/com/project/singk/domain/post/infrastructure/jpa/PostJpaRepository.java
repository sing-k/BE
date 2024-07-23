package com.project.singk.domain.post.infrastructure.jpa;

import com.project.singk.domain.post.domain.Post;
import com.project.singk.domain.post.infrastructure.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<PostEntity, Long> {
    Page<PostEntity> findAllByIsDeletedFalse(Pageable pageable);
    Page<PostEntity> findByMemberIdAndIsDeletedFalse(Long memberId, Pageable pageable);
}
