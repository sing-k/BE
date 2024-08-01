package com.project.singk.domain.like.infrastructure;

import com.project.singk.domain.like.domain.FreeCommentLike;
import com.project.singk.domain.like.infrastructure.entity.FreeCommentLikeEntity;
import com.project.singk.domain.like.infrastructure.jpa.FreeCommentLikeJpaRepository;
import com.project.singk.domain.like.service.port.FreeCommentLikeRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.project.singk.domain.comment.infrastructure.entity.QFreeCommentEntity.freeCommentEntity;
import static com.project.singk.domain.like.infrastructure.entity.QFreeCommentLikeEntity.freeCommentLikeEntity;
import static com.project.singk.domain.member.infrastructure.QMemberEntity.memberEntity;

@Repository
@RequiredArgsConstructor
public class FreeCommentLikeRepositoryImpl implements FreeCommentLikeRepository {

    private final FreeCommentLikeJpaRepository jpaRepository;
    private final JPAQueryFactory queryFactory;
    @Override
    public FreeCommentLike save(FreeCommentLike like){ return jpaRepository.save(FreeCommentLikeEntity.from(like)).toModel();}

    @Override
    public Optional<FreeCommentLike> findByMemberIdAndCommentId(Long memberId, Long commentId) {
        return Optional.ofNullable(queryFactory.selectFrom(freeCommentLikeEntity)
                .join(freeCommentLikeEntity.member, memberEntity)
                .join(freeCommentLikeEntity.comment, freeCommentEntity)
                .where(freeCommentLikeEntity.member.id.eq(memberId)
                        .and(freeCommentLikeEntity.comment.id.eq(commentId)))
                .fetchOne()
        ).map(FreeCommentLikeEntity::toModel);
    }

    @Override
    public boolean existsByMemberIdAndCommentId(Long memberId, Long commentId) {
        if (memberId == null) return false;

        return queryFactory.selectFrom(freeCommentLikeEntity)
                .where(freeCommentLikeEntity.member.id.eq(memberId)
                        .and(freeCommentLikeEntity.comment.id.eq(commentId)))
                .fetchFirst() != null;
    }

    @Override
    public void deleteById(Long commentId) {
        jpaRepository.deleteById(commentId);
    }

}
