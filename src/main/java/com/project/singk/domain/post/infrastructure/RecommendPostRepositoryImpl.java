package com.project.singk.domain.post.infrastructure;

import com.project.singk.domain.post.controller.request.FilterSort;
import com.project.singk.domain.post.controller.request.PostSort;
import com.project.singk.domain.post.domain.Post;
import com.project.singk.domain.post.domain.RecommendPost;
import com.project.singk.domain.post.infrastructure.entity.PostEntity;
import com.project.singk.domain.post.infrastructure.entity.RecommendPostEntity;
import com.project.singk.domain.post.infrastructure.jpa.RecommendPostJpaRepository;
import com.project.singk.domain.post.service.port.RecommendPostRepository;
import com.project.singk.domain.review.controller.request.ReviewSort;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.project.singk.domain.album.infrastructure.entity.QAlbumEntity.albumEntity;
import static com.project.singk.domain.post.infrastructure.entity.QRecommendPostEntity.recommendPostEntity;
import static com.project.singk.domain.review.infrastructure.QAlbumReviewEntity.albumReviewEntity;
import static com.project.singk.domain.review.infrastructure.QAlbumReviewStatisticsEntity.albumReviewStatisticsEntity;


@Repository
@RequiredArgsConstructor
public class RecommendPostRepositoryImpl implements RecommendPostRepository {

    private final RecommendPostJpaRepository recommendPostJpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public RecommendPost save(RecommendPost post) {
        return recommendPostJpaRepository.save(RecommendPostEntity.from(post)).toModel();
    }

    @Override
    public Optional<RecommendPost> findById(Long postId) {
        return recommendPostJpaRepository.findById(postId).map(RecommendPostEntity::toModel);
    }

    @Override
    public Page<RecommendPost> findAll(int offset, int limit, String sort, String filter, String keyword) {
        List<RecommendPost> posts = queryFactory.selectFrom(recommendPostEntity)
                .where(filter(FilterSort.valueOf(filter), keyword))
                .orderBy(order(PostSort.valueOf(sort)))
                .offset(offset)
                .limit(limit)
                .fetch().stream()
                .map(RecommendPostEntity::toModel)
                .toList();

        Long count = queryFactory.select(recommendPostEntity.count())
                .from(recommendPostEntity)
                .fetchOne();

        Pageable pageable = PageRequest.ofSize(limit);

        return new PageImpl<>(posts, pageable, count);
    }

    @Override
    public Page<RecommendPost> findAllByMemberId(Long memberId, int offset, int limit, String sort, String filter, String keyword) {
        List<RecommendPost> posts = queryFactory.selectFrom(recommendPostEntity)
                .where(recommendPostEntity.member.id.eq(memberId)
                        .and(filter(FilterSort.valueOf(filter), keyword)))
                .orderBy(order(PostSort.valueOf(sort)))
                .offset(offset)
                .limit(limit)
                .fetch().stream()
                .map(RecommendPostEntity::toModel)
                .toList();

        Long count = queryFactory.select(recommendPostEntity.count())
                .from(recommendPostEntity)
                .where(recommendPostEntity.member.id.eq(memberId))
                .fetchOne();

        Pageable pageable = PageRequest.ofSize(limit);

        return new PageImpl<>(posts, pageable, count);
    }
    private OrderSpecifier order(PostSort sort) {
        return switch (sort) {
            case LATEST -> new OrderSpecifier(Order.DESC, recommendPostEntity.createdAt);
            case LIKES -> new OrderSpecifier(Order.DESC, recommendPostEntity.likes);
        };
    }

    private BooleanExpression filter(FilterSort filter, String keyword) {
        if (!StringUtils.hasText(keyword)) return null;

        return switch (filter) {
            case TITLE -> recommendPostEntity.title.contains(keyword);
            case CONTENT -> recommendPostEntity.content.contains(keyword);
            case WRITER -> recommendPostEntity.member.nickname.contains(keyword);
        };
    }
    @Override
    public RecommendPost getById(Long postId) {
        return findById(postId).orElseThrow(() -> new ApiException(AppHttpStatus.NOT_FOUND_POST));
    }

    @Override
    public void deleteById(Long postId) {recommendPostJpaRepository.deleteById(postId);
    }
}
