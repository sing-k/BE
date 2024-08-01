package com.project.singk.domain.like.infrastructure;

import com.project.singk.domain.like.domain.RecommendPostLike;
import com.project.singk.domain.like.infrastructure.entity.RecommendPostLikeEntity;
import com.project.singk.domain.like.infrastructure.jpa.RecommendPostLikeJpaRepository;
import com.project.singk.domain.like.service.port.RecommendPostLikeRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.project.singk.domain.like.infrastructure.entity.QRecommendPostLikeEntity.recommendPostLikeEntity;
import static com.project.singk.domain.member.infrastructure.QMemberEntity.memberEntity;
import static com.project.singk.domain.post.infrastructure.entity.QRecommendPostEntity.recommendPostEntity;

@Repository
@RequiredArgsConstructor
public class RecommendPostLikeRepositoryImpl implements RecommendPostLikeRepository {
    private final RecommendPostLikeJpaRepository jpaRepository;
    private final JPAQueryFactory queryFactory;
    @Override
    public RecommendPostLike save(RecommendPostLike like){ return jpaRepository.save(RecommendPostLikeEntity.from(like)).toModel();}

    @Override
    public Optional<RecommendPostLike> findByMemberIdAndPostId(Long memberId, Long postId) {
        return Optional.ofNullable(queryFactory.selectFrom(recommendPostLikeEntity)
                .join(recommendPostLikeEntity.member, memberEntity)
                .join(recommendPostLikeEntity.post, recommendPostEntity)
                .where(recommendPostLikeEntity.member.id.eq(memberId)
                        .and(recommendPostLikeEntity.post.id.eq(postId)))
                .fetchOne())
                .map(RecommendPostLikeEntity::toModel);
    }

    @Override
    public boolean existsByMemberIdAndPostId(Long memberId, Long postId) {
        if (memberId == null) return false;

        return queryFactory.selectFrom(recommendPostLikeEntity)
                .where(recommendPostLikeEntity.member.id.eq(memberId)
                        .and(recommendPostLikeEntity.post.id.eq(postId)))
                .fetchFirst() != null; // count 쿼리 -> limit 쿼리
    }

    @Override
    public void deleteById(Long likeId){jpaRepository.deleteById(likeId);}
}
