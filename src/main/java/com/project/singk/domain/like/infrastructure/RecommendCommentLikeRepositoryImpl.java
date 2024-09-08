package com.project.singk.domain.like.infrastructure;

import com.project.singk.domain.like.domain.RecommendCommentLike;
import com.project.singk.domain.like.infrastructure.entity.RecommendCommentLikeEntity;
import com.project.singk.domain.like.infrastructure.jpa.RecommendCommentLikeJpaRepository;
import com.project.singk.domain.like.service.port.RecommendCommentLikeRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.project.singk.domain.comment.infrastructure.entity.QRecommendCommentEntity.recommendCommentEntity;
import static com.project.singk.domain.like.infrastructure.entity.QRecommendCommentLikeEntity.recommendCommentLikeEntity;
import static com.project.singk.domain.member.infrastructure.QMemberEntity.memberEntity;

@Repository
@RequiredArgsConstructor
public class RecommendCommentLikeRepositoryImpl implements RecommendCommentLikeRepository {
    private final RecommendCommentLikeJpaRepository jpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public RecommendCommentLike save(RecommendCommentLike like){
        return jpaRepository.save(RecommendCommentLikeEntity.from(like)).toModel();
    }

    @Override
    public Optional<RecommendCommentLike> findByMemberIdAndCommentId(Long memberId, Long commentId) {
        return Optional.ofNullable(queryFactory.selectFrom(recommendCommentLikeEntity)
                .join(recommendCommentLikeEntity.member, memberEntity)
                .join(recommendCommentLikeEntity.comment, recommendCommentEntity)
                .where(recommendCommentLikeEntity.member.id.eq(memberId)
                        .and(recommendCommentLikeEntity.comment.id.eq(commentId)))
                .fetchOne()
        ).map(RecommendCommentLikeEntity::toModel);
    }

    @Override
    public boolean existsByMemberIdAndCommentId(Long memberId, Long commentId) {
        if (memberId == null) return false;

        return queryFactory.selectFrom(recommendCommentLikeEntity)
                .where(recommendCommentLikeEntity.member.id.eq(memberId)
                        .and(recommendCommentLikeEntity.comment.id.eq(commentId)))
                .fetchFirst() != null;
    }

    @Override
    public void deleteById(Long likeId) {
        jpaRepository.deleteById(likeId);
    }
}
