package com.project.singk.domain.like.infrastructure;

import com.project.singk.domain.like.domain.FreePostLike;
import com.project.singk.domain.like.infrastructure.entity.FreePostLikeEntity;
import com.project.singk.domain.like.infrastructure.jpa.FreePostLikeJpaRepository;
import com.project.singk.domain.like.service.port.FreePostLikeRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.project.singk.domain.like.infrastructure.entity.QFreePostLikeEntity.freePostLikeEntity;
import static com.project.singk.domain.member.infrastructure.QMemberEntity.memberEntity;
import static com.project.singk.domain.post.infrastructure.entity.QFreePostEntity.freePostEntity;

@Repository
@RequiredArgsConstructor
public class FreePostLikeRepositoryImpl implements FreePostLikeRepository {
    private final FreePostLikeJpaRepository jpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public FreePostLike save(FreePostLike like){ return jpaRepository.save(FreePostLikeEntity.from(like)).toModel();}

    @Override
    public Optional<FreePostLike> findByMemberIdAndPostId(Long memberId, Long postId) {
        return Optional.ofNullable(queryFactory.selectFrom(freePostLikeEntity)
                .join(freePostLikeEntity.member, memberEntity)
                .join(freePostLikeEntity.post, freePostEntity)
                .where(freePostLikeEntity.member.id.eq(memberId)
                        .and(freePostLikeEntity.post.id.eq(postId)))
                .fetchOne())
                .map(FreePostLikeEntity::toModel);
    }

    @Override
    public boolean existsByMemberIdAndPostId(Long memberId, Long postId) {
        if (memberId == null) return false;

        return queryFactory.selectFrom(freePostLikeEntity)
                .where(freePostLikeEntity.member.id.eq(memberId)
                        .and(freePostLikeEntity.post.id.eq(postId)))
                .fetchFirst() != null;
    }

    @Override
    public void deleteById(Long likeId){jpaRepository.deleteById(likeId);}
}
