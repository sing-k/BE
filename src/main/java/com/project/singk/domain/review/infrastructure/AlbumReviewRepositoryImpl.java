package com.project.singk.domain.review.infrastructure;

import com.project.singk.domain.album.infrastructure.entity.QAlbumEntity;
import com.project.singk.domain.member.domain.Gender;
import com.project.singk.domain.member.infrastructure.QMemberEntity;
import com.project.singk.domain.review.domain.AlbumReview;
import com.project.singk.domain.review.domain.AlbumReviewStatistics;
import com.project.singk.domain.review.service.port.AlbumReviewRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
    public AlbumReview getById(Long id) {
        return findById(id).orElseThrow(() -> new ApiException(AppHttpStatus.NOT_FOUND_ALBUM_REVIEW));
    }

    @Override
    public Optional<AlbumReview> findById(Long id) {
        return albumReviewJpaRepository.findById(id).map(AlbumReviewEntity::toModel);
    }

    @Override
    public AlbumReviewStatistics getAlbumReviewStatisticsByAlbumId(String albumId) {
        QAlbumReviewEntity albumReview = QAlbumReviewEntity.albumReviewEntity;
        QMemberEntity member = QMemberEntity.memberEntity;
        QAlbumEntity album = QAlbumEntity.albumEntity;

        return jpaQueryFactory.select(Projections.constructor(AlbumReviewStatistics.class,
                albumReview.count(),
                albumReview.score.sum(),
                albumReview.score.avg(),
                albumReview.score.when(1).then(1).otherwise(0).sum().as("score1Count"),
                albumReview.score.when(2).then(1).otherwise(0).sum().as("score2Count"),
                albumReview.score.when(3).then(1).otherwise(0).sum().as("score3Count"),
                albumReview.score.when(4).then(1).otherwise(0).sum().as("score4Count"),
                albumReview.score.when(5).then(1).otherwise(0).sum().as("score5Count"),
                member.gender.when(Gender.MALE).then(1).otherwise(0).sum().as("maleCount"),
                member.gender.when(Gender.FEMALE).then(1).otherwise(0).sum().as("femaleCount")
                ))
                .from(albumReview)
                .leftJoin(albumReview.member, member)
                .leftJoin(albumReview.album, album)
                .where(album.id.eq(albumId))
                .groupBy(album.id)
                .fetchOne();
    }
}
