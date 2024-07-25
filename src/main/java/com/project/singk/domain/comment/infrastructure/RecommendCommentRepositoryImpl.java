package com.project.singk.domain.comment.infrastructure;

import com.project.singk.domain.comment.domain.RecommendComment;
import com.project.singk.domain.comment.infrastructure.entity.RecommendCommentEntity;
import com.project.singk.domain.comment.infrastructure.jpa.RecommendCommentJpaRepository;
import com.project.singk.domain.comment.service.port.RecommendCommentRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class RecommendCommentRepositoryImpl implements RecommendCommentRepository {
    private final RecommendCommentJpaRepository recommendCommentJpaRepository;

    @Override
    public RecommendComment save(RecommendComment comment){
        return recommendCommentJpaRepository.save(RecommendCommentEntity.from(comment)).toModel();
    }

    @Override
    public RecommendComment findById(Long id){
        return recommendCommentJpaRepository.findById(id).orElseThrow(() -> new ApiException(AppHttpStatus.NOT_FOUND)).toModel();
    }
    @Override
    public void delete(Long id){
        recommendCommentJpaRepository.deleteById(id);
    }
}
