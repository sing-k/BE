package com.project.singk.domain.review.infrastructure;

import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.album.infrastructure.entity.AlbumEntity;
import com.project.singk.domain.album.infrastructure.entity.QAlbumEntity;
import com.project.singk.domain.member.domain.Gender;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.infrastructure.MemberEntity;
import com.project.singk.domain.member.infrastructure.QMemberEntity;
import com.project.singk.domain.review.controller.request.ReviewSort;
import com.project.singk.domain.review.domain.AlbumReview;
import com.project.singk.domain.review.domain.AlbumReviewStatistics;
import com.project.singk.domain.review.domain.QAlbumReviewStatistics;
import com.project.singk.domain.review.service.port.AlbumReviewRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
    public List<AlbumReview> getAllByAlbumId(String albumId, ReviewSort sort) {
        QAlbumReviewEntity albumReview = QAlbumReviewEntity.albumReviewEntity;
        QMemberEntity member = QMemberEntity.memberEntity;
        QAlbumEntity album = QAlbumEntity.albumEntity;

        return jpaQueryFactory.select(Projections.fields(AlbumReviewEntity.class,
                    albumReview.id,
                    albumReview.content,
                    albumReview.score,
                    albumReview.prosCount,
                    albumReview.consCount,
                    albumReview.createdAt,
                    albumReview.member,
                    albumReview.album
                ))
                .from(albumReview)
                .innerJoin(albumReview.member, member)
                .innerJoin(albumReview.album, album)
                .where(album.id.eq(albumId))
                .orderBy(createOrderSpecifier(sort, albumReview))
                .fetch()
                .stream()
                .map(AlbumReviewEntity::toModel)
                .toList();
    }

    private OrderSpecifier createOrderSpecifier(ReviewSort sort, QAlbumReviewEntity albumReview) {
        return switch (sort) {
            case NEW -> new OrderSpecifier(Order.DESC, albumReview.createdAt);
            case LIKES -> new OrderSpecifier(Order.DESC, albumReview.prosCount);
        };
    }

    @Override
    public AlbumReviewStatistics getAlbumReviewStatisticsByAlbumId(String albumId) {
        QAlbumReviewEntity albumReview = QAlbumReviewEntity.albumReviewEntity;
        QMemberEntity member = QMemberEntity.memberEntity;
        QAlbumEntity album = QAlbumEntity.albumEntity;

        return jpaQueryFactory.select(new QAlbumReviewStatistics(
                albumReview.count().as("totalReviewer"),
                albumReview.score.sum().as("totalScore"),
                albumReview.score.when(1).then(1).otherwise(0).sum().as("score1Count"),
                albumReview.score.when(2).then(1).otherwise(0).sum().as("score2Count"),
                albumReview.score.when(3).then(1).otherwise(0).sum().as("score3Count"),
                albumReview.score.when(4).then(1).otherwise(0).sum().as("score4Count"),
                albumReview.score.when(5).then(1).otherwise(0).sum().as("score5Count"),
                member.gender.when(Gender.MALE).then(1).otherwise(0).sum().as("maleCount"),
                member.gender.when(Gender.MALE).then(albumReview.score).otherwise(0).sum().as("maleTotalScore"),
                member.gender.when(Gender.FEMALE).then(1).otherwise(0).sum().as("femaleCount"),
                member.gender.when(Gender.FEMALE).then(albumReview.score).otherwise(0).sum().as("femaleTotalScore")
                ))
                .from(albumReview)
                .leftJoin(albumReview.member, member)
                .leftJoin(albumReview.album, album)
                .where(album.id.eq(albumId))
                .groupBy(album.id)
                .fetchOne();
    }

    @Override
    public void delete(AlbumReview albumReview) {
        albumReviewJpaRepository.delete(AlbumReviewEntity.from(albumReview));
    }
}
