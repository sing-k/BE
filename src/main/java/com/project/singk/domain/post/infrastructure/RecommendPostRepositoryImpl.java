package com.project.singk.domain.post.infrastructure;

import com.project.singk.domain.post.infrastructure.jpa.RecommendPostJpaRepository;
import com.project.singk.domain.post.service.port.RecommendPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class RecommendPostRepositoryImpl implements RecommendPostRepository {

    private final RecommendPostJpaRepository recommendPostJpaRepository;
}
