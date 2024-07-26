package com.project.singk.domain.like.infrastructure;

import com.project.singk.domain.like.domain.PostCommentLike;
import com.project.singk.domain.like.domain.RecommendCommentLike;
import com.project.singk.domain.like.infrastructure.entity.PostCommentLikeEntity;
import com.project.singk.domain.like.infrastructure.entity.RecommendCommentLikeEntity;
import com.project.singk.domain.like.infrastructure.jpa.PostCommentLikeJpaRepository;
import com.project.singk.domain.like.infrastructure.jpa.RecommendCommentLikeJpaRepository;
import com.project.singk.domain.like.service.port.RecommendCommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RecommendCommentLikeRepositoryImpl implements RecommendCommentLikeRepository {
    private final RecommendCommentLikeJpaRepository jpaRepository;

    @Override
    public RecommendCommentLike save(RecommendCommentLike like){ return jpaRepository.save(RecommendCommentLikeEntity.from(like).toModel());}

    @Override
    public void delete(Long id){jpaRepository.deleteById(id);}
}
