package com.project.singk.domain.comment.infrastructure;

import com.project.singk.domain.comment.domain.RecommendComment;
import com.project.singk.domain.comment.domain.CommentSimplified;
import com.project.singk.domain.comment.infrastructure.entity.RecommendCommentEntity;
import com.project.singk.domain.comment.infrastructure.jpa.RecommendCommentJpaRepository;
import com.project.singk.domain.comment.service.port.RecommendCommentRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.project.singk.domain.comment.infrastructure.entity.QRecommendCommentEntity.recommendCommentEntity;

@Repository
@RequiredArgsConstructor
public class RecommendCommentRepositoryImpl implements RecommendCommentRepository {
    private final RecommendCommentJpaRepository recommendCommentJpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public RecommendComment save(RecommendComment comment){
        return recommendCommentJpaRepository.save(RecommendCommentEntity.from(comment)).toModel();
    }

    @Override
    public RecommendComment getById(Long commentId) {
        return findById(commentId).orElseThrow(() -> new ApiException(AppHttpStatus.NOT_FOUND_COMMENT));
    }

    @Override
    public Optional<RecommendComment> findById(Long commentId){
        return recommendCommentJpaRepository.findById(commentId).map(RecommendCommentEntity::toModel);
    }

    @Override
    public void deleteById(Long commentId) {
        recommendCommentJpaRepository.deleteById(commentId);
    }

    @Override
    public void deleteByPostId(Long postId) {
        queryFactory.delete(recommendCommentEntity)
                .where(recommendCommentEntity.post.id.eq(postId))
                .execute();
    }

    @Override
    public List<CommentSimplified> findAllByPostId(Long postId) {
        return queryFactory.selectFrom(recommendCommentEntity)
                .where(recommendCommentEntity.post.id.eq(postId))
                .orderBy(recommendCommentEntity.parent.id.asc().nullsFirst())
                .fetch().stream()
                .map(RecommendCommentEntity::simplified)
                .toList();
    }

    @Override
    public List<CommentSimplified> findAllByMemberId(Long memberId) {
        return queryFactory.selectFrom(recommendCommentEntity)
                .where(recommendCommentEntity.member.id.eq(memberId))
                .orderBy(recommendCommentEntity.parent.id.asc().nullsFirst())
                .fetch().stream()
                .map(RecommendCommentEntity::simplified)
                .toList();
    }
}
