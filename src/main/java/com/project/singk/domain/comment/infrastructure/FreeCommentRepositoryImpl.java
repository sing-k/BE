package com.project.singk.domain.comment.infrastructure;

import com.project.singk.domain.comment.domain.CommentSimplified;
import com.project.singk.domain.comment.domain.FreeComment;
import com.project.singk.domain.comment.infrastructure.entity.FreeCommentEntity;
import com.project.singk.domain.comment.infrastructure.entity.RecommendCommentEntity;
import com.project.singk.domain.comment.infrastructure.jpa.FreeCommentJpaRepository;
import com.project.singk.domain.comment.service.port.FreeCommentRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.project.singk.domain.comment.infrastructure.entity.QFreeCommentEntity.freeCommentEntity;
import static com.project.singk.domain.comment.infrastructure.entity.QRecommendCommentEntity.recommendCommentEntity;

@Repository
@RequiredArgsConstructor
public class FreeCommentRepositoryImpl implements FreeCommentRepository {
    private final FreeCommentJpaRepository freeCommentJpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public FreeComment save(FreeComment comment){
        return freeCommentJpaRepository.save(FreeCommentEntity.from(comment)).toModel();
    }

    @Override
    public Optional<FreeComment> findById(Long commentId){
        return freeCommentJpaRepository.findById(commentId).map(FreeCommentEntity::toModel);
    }

    @Override
    public FreeComment getById(Long commentId) {
        return findById(commentId).orElseThrow(() -> new ApiException(AppHttpStatus.NOT_FOUND_COMMENT));
    }

    @Override
    public void deleteById(Long commentId) {
        freeCommentJpaRepository.deleteById(commentId);
    }

    @Override
    public List<CommentSimplified> findAllByPostId(Long postId) {
        return queryFactory.selectFrom(freeCommentEntity)
                .where(freeCommentEntity.post.id.eq(postId))
                .orderBy(freeCommentEntity.parent.id.asc().nullsFirst())
                .fetch().stream()
                .map(FreeCommentEntity::simplified)
                .toList();
    }

}
