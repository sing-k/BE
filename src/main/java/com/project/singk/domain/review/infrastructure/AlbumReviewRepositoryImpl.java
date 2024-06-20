package com.project.singk.domain.review.infrastructure;

import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.album.infrastructure.entity.AlbumEntity;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.infrastructure.MemberEntity;
import com.project.singk.domain.review.controller.request.ReviewSort;
import com.project.singk.domain.review.domain.AlbumReview;
import com.project.singk.domain.review.service.port.AlbumReviewRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.project.singk.domain.album.infrastructure.entity.QAlbumEntity.albumEntity;
import static com.project.singk.domain.member.infrastructure.QMemberEntity.memberEntity;
import static com.project.singk.domain.review.infrastructure.QAlbumReviewEntity.albumReviewEntity;

@Repository
@RequiredArgsConstructor
public class AlbumReviewRepositoryImpl implements AlbumReviewRepository {
    private final AlbumReviewJpaRepository albumReviewJpaRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public AlbumReview save(AlbumReview albumReview) {
        return albumReviewJpaRepository.save(AlbumReviewEntity.from(albumReview)).toModel();
    }

    @Override
    public List<AlbumReview> saveAll(List<AlbumReview> albumReviews) {
        return albumReviews.stream().map(this::save).toList();
    }

    @Override
    public AlbumReview getById(Long id) {
        return findById(id).orElseThrow(() -> new ApiException(AppHttpStatus.NOT_FOUND_ALBUM_REVIEW));
    }

    @Override
    public Optional<AlbumReview> findById(Long id) {
        return albumReviewJpaRepository.findById(id).map(AlbumReviewEntity::toModel);
    }

    @Override
    public boolean existsByMemberAndAlbum(Member member, Album album) {
        return albumReviewJpaRepository.existsByMemberAndAlbum(
                MemberEntity.from(member),
                AlbumEntity.from(album)
        );
    }

    @Override
    public Page<AlbumReview> getAllByAlbumId(String albumId, int offset, int limit, String sort) {

        List<AlbumReview> reviews = jpaQueryFactory.select(albumReviewEntity)
                .from(albumReviewEntity)
                .innerJoin(albumReviewEntity.member, memberEntity).fetchJoin()
                .innerJoin(albumReviewEntity.album, albumEntity).fetchJoin()
                .where(albumEntity.id.eq(albumId))
                .orderBy(createOrderSpecifier(ReviewSort.valueOf(sort)))
                .fetch()
                .stream()
                .map(AlbumReviewEntity::toModel)
                .toList();

        JPAQuery<Long> count = jpaQueryFactory.select(albumReviewEntity.count())
                .from(albumReviewEntity)
                .join(albumReviewEntity.member, memberEntity)
                .join(albumReviewEntity.album, albumEntity)
                .where(albumEntity.id.eq(albumId));

        Pageable pageable = PageRequest.ofSize(limit);

        return PageableExecutionUtils.getPage(reviews, pageable, count::fetchOne);
    }

    @Override
    public Page<AlbumReview> getAllByMemberId(Long memberId, int offset, int limit, String sort) {
        List<AlbumReview> reviews = jpaQueryFactory.select(albumReviewEntity)
                .from(albumReviewEntity)
                .innerJoin(albumReviewEntity.member, memberEntity).fetchJoin()
                .innerJoin(albumReviewEntity.album, albumEntity).fetchJoin()
                .where(memberEntity.id.eq(memberId))
                .orderBy(createOrderSpecifier(ReviewSort.valueOf(sort)))
                .offset(offset)
                .limit(limit)
                .fetch().stream()
                .map(AlbumReviewEntity::toModel)
                .toList();

        JPAQuery<Long> count = jpaQueryFactory.select(albumReviewEntity.count())
                .from(albumReviewEntity)
                .join(albumReviewEntity.member, memberEntity)
                .join(albumReviewEntity.album, albumEntity)
                .where(memberEntity.id.eq(memberId));

        Pageable pageable = PageRequest.ofSize(limit);

        return PageableExecutionUtils.getPage(reviews, pageable, count::fetchOne);
    }

    private OrderSpecifier createOrderSpecifier(ReviewSort sort) {
        return switch (sort) {
            case NEW -> new OrderSpecifier(Order.DESC, albumReviewEntity.createdAt);
            case LIKES -> new OrderSpecifier(Order.DESC, albumReviewEntity.prosCount);
        };
    }

    @Override
    public void delete(AlbumReview albumReview) {
        albumReviewJpaRepository.delete(AlbumReviewEntity.from(albumReview));
    }
}
