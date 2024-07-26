package com.project.singk.domain.comment.infrastructure;

import com.project.singk.domain.comment.domain.PostComment;
import com.project.singk.domain.comment.infrastructure.entity.PostCommentEntity;
import com.project.singk.domain.comment.infrastructure.jpa.PostCommentJpaRepository;
import com.project.singk.domain.comment.service.port.PostCommentRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostCommentRepositoryImpl implements PostCommentRepository {
    private final PostCommentJpaRepository postCommentJpaRepository;

    @Override
    public PostComment save(PostComment comment){
        return postCommentJpaRepository.save(PostCommentEntity.from(comment)).toModel();
    }

    @Override
    public PostComment findById(Long id){
        return postCommentJpaRepository.findById(id).orElseThrow(() -> new ApiException(AppHttpStatus.NOT_FOUND)).toModel();
    }
    @Override
    public void delete(Long id){
        postCommentJpaRepository.deleteById(id);
    }
}
