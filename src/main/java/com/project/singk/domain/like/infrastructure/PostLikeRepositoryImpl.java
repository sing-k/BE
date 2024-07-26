package com.project.singk.domain.like.infrastructure;

import com.project.singk.domain.like.domain.PostLike;
import com.project.singk.domain.like.infrastructure.entity.PostLikeEntity;
import com.project.singk.domain.like.infrastructure.jpa.PostLikeJpaRepository;
import com.project.singk.domain.like.service.port.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostLikeRepositoryImpl implements PostLikeRepository {
    private final PostLikeJpaRepository jpaRepository;

    @Override
    public PostLike save(PostLike like){ return jpaRepository.save(PostLikeEntity.from(like).toModel());}

    @Override
    public void delete(Long id){jpaRepository.deleteById(id);}
}
