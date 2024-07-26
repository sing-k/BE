package com.project.singk.domain.like.infrastructure;

import com.project.singk.domain.like.domain.PostCommentLike;
import com.project.singk.domain.like.infrastructure.entity.PostCommentLikeEntity;
import com.project.singk.domain.like.infrastructure.jpa.PostCommentLikeJpaRepository;
import com.project.singk.domain.like.service.port.PostCommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostCommentLikeRepositoryImpl implements PostCommentLikeRepository {

    private final PostCommentLikeJpaRepository jpaRepository;

    @Override
    public PostCommentLike save(PostCommentLike like){ return jpaRepository.save(PostCommentLikeEntity.from(like).toModel());}

    @Override
    public void delete(Long id){jpaRepository.deleteById(id);}
}
