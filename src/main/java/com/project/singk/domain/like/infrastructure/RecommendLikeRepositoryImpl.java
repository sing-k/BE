package com.project.singk.domain.like.infrastructure;

import com.project.singk.domain.like.domain.RecommendLike;
import com.project.singk.domain.like.infrastructure.entity.RecommendLikeEntity;
import com.project.singk.domain.like.infrastructure.jpa.RecommendLikeJpaRepository;
import com.project.singk.domain.like.service.port.RecommendLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RecommendLikeRepositoryImpl implements RecommendLikeRepository {
    private final RecommendLikeJpaRepository jpaRepository;

    @Override
    public RecommendLike save(RecommendLike like){ return jpaRepository.save(RecommendLikeEntity.from(like).toModel());}

    @Override
    public void delete(Long id){jpaRepository.deleteById(id);}
}
