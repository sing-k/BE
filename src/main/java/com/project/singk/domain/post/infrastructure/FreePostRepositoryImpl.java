package com.project.singk.domain.post.infrastructure;

import com.project.singk.domain.post.controller.request.FilterSort;
import com.project.singk.domain.post.controller.request.PostSort;
import com.project.singk.domain.post.domain.FreePost;
import com.project.singk.domain.post.infrastructure.entity.FreePostEntity;
import com.project.singk.domain.post.infrastructure.jpa.FreePostJpaRepository;
import com.project.singk.domain.post.service.port.FreePostRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static com.project.singk.domain.post.infrastructure.entity.QFreePostEntity.freePostEntity;
import static com.project.singk.domain.post.infrastructure.entity.QRecommendPostEntity.recommendPostEntity;

@Repository
@RequiredArgsConstructor
public class FreePostRepositoryImpl implements FreePostRepository {

    private final FreePostJpaRepository freePostJpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public FreePost save(FreePost freePost) {
        return freePostJpaRepository.save(FreePostEntity.from(freePost)).toModel();
    }

    @Override
    public List<FreePost> saveAll(List<FreePost> post) {
        return freePostJpaRepository.saveAll(post.stream()
                .map(FreePostEntity::from)
                .toList()).stream()
                .map(FreePostEntity::toModel)
                .toList();
    }

    @Override
    public void deleteById(Long postId) {
        freePostJpaRepository.deleteById(postId);
    }

    @Override
    public Optional<FreePost> findById(Long id) {
        return freePostJpaRepository.findById(id).map(FreePostEntity::toModel);
    }

    @Override
    public Page<FreePost> findAll(int offset, int limit, String sort, String filter, String keyword) {
        List<FreePost> posts = queryFactory.selectFrom(freePostEntity)
                .where(search(filter, keyword))
                .orderBy(order(PostSort.valueOf(sort)))
                .offset(offset)
                .limit(limit)
                .fetch().stream()
                .map(FreePostEntity::toModel)
                .toList();

        Long count = queryFactory.select(freePostEntity.count())
                .from(freePostEntity)
                .where(search(filter, keyword))
                .fetchOne();

        Pageable pageable = PageRequest.ofSize(limit);

        return new PageImpl<>(posts, pageable, count);
    }

    @Override
    public Page<FreePost> findAllByMemberId(Long memberId, int offset, int limit) {
        List<FreePost> posts = queryFactory.selectFrom(freePostEntity)
                .where(freePostEntity.member.id.eq(memberId))
                .orderBy(order(PostSort.LATEST))
                .offset(offset)
                .limit(limit)
                .fetch().stream()
                .map(FreePostEntity::toModel)
                .toList();

        Long count = queryFactory.select(freePostEntity.count())
                .from(freePostEntity)
                .where(freePostEntity.member.id.eq(memberId))
                .fetchOne();

        Pageable pageable = PageRequest.ofSize(limit);

        return new PageImpl<>(posts, pageable, count);
    }

    private OrderSpecifier order(PostSort sort) {
        return switch (sort) {
            case LATEST -> new OrderSpecifier(Order.DESC, freePostEntity.createdAt);
            case LIKES -> new OrderSpecifier(Order.DESC, freePostEntity.likes);
        };
    }

    private BooleanExpression search(String filter, String keyword) {
        if (!StringUtils.hasText(filter) || !StringUtils.hasText(keyword)) return null;

        FilterSort filterSort = FilterSort.valueOf(filter);

        return switch (filterSort) {
            case TITLE -> freePostEntity.title.contains(keyword);
            case CONTENT -> freePostEntity.content.contains(keyword);
            case WRITER -> freePostEntity.member.nickname.contains(keyword);
        };
    }

    @Override
    public FreePost getById(Long postId) {
        return findById(postId).orElseThrow(() -> new ApiException(AppHttpStatus.NOT_FOUND_POST));
    }

}
