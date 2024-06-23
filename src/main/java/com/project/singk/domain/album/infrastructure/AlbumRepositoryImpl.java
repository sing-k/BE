package com.project.singk.domain.album.infrastructure;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import com.project.singk.domain.album.infrastructure.entity.*;
import com.project.singk.domain.album.infrastructure.jpa.AlbumJpaRepository;
import com.project.singk.domain.review.domain.AlbumReviewStatistics;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.album.service.port.AlbumRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;

import lombok.RequiredArgsConstructor;

import static com.project.singk.domain.album.infrastructure.entity.QAlbumArtistEntity.albumArtistEntity;
import static com.project.singk.domain.album.infrastructure.entity.QAlbumEntity.albumEntity;
import static com.project.singk.domain.album.infrastructure.entity.QAlbumImageEntity.albumImageEntity;
import static com.project.singk.domain.album.infrastructure.entity.QTrackEntity.trackEntity;
import static com.project.singk.domain.review.infrastructure.QAlbumReviewStatisticsEntity.albumReviewStatisticsEntity;

@Repository
@RequiredArgsConstructor
public class AlbumRepositoryImpl implements AlbumRepository {
	private final AlbumJpaRepository albumJpaRepository;
    private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Album save(Album album) {
		return albumJpaRepository.save(AlbumEntity.from(album)).toModel();
	}

    @Override
    public List<Album> saveAll(List<Album> albums) {
        return albums.stream().map(this::save).toList();
    }

    @Override
    public boolean existsById(String albumId) {
        return albumJpaRepository.existsById(albumId);
    }

    @Override
	public Album getById(String id) {
		return findById(id)
			.orElseThrow(() -> new ApiException(AppHttpStatus.NOT_FOUND_ALBUM));
	}

    @Override
    public Album getByIdWithStatistics(String albumId) {
        return findByIdWithStatistics(albumId)
                .orElseThrow(() -> new ApiException(AppHttpStatus.NOT_FOUND_ALBUM));
    }

    @Override
    public AlbumReviewStatistics getAlbumReviewStatisticsByAlbumId(String albumId) {

        return jpaQueryFactory.select(albumReviewStatisticsEntity)
                .from(albumEntity)
                .innerJoin(albumEntity.statistics, albumReviewStatisticsEntity)
                .where(albumEntity.id.eq(albumId))
                .fetchOne()
                .toModel();
    }

    @Override
    public Optional<Album> findByIdWithStatistics(String albumId) {
        return Optional.ofNullable(jpaQueryFactory.select(albumEntity)
                .from(albumEntity)
                .where(albumEntity.id.eq(albumId))
                .innerJoin(albumEntity.statistics, albumReviewStatisticsEntity).fetchJoin()
                .fetchOne())
                .map(AlbumEntity::toModel);
    }

    @Override
	public Optional<Album> findById(String id) {
        return Optional.ofNullable(jpaQueryFactory
                .selectFrom(albumEntity)
                .where(albumEntity.id.eq(id))
                .leftJoin(albumEntity.tracks, trackEntity)
                .leftJoin(albumEntity.images, albumImageEntity)
                .leftJoin(albumEntity.artists, albumArtistEntity)
                .fetchOne())
                .map(AlbumEntity::toModel);
	}

    @Override
    public Page<Album> findAllByModifiedAt(String cursorId, String cursorDate, int limit) {
        List<Album> albums = jpaQueryFactory.select(albumEntity)
                .from(albumEntity)
                .innerJoin(albumEntity.statistics, albumReviewStatisticsEntity).fetchJoin()
                .where(cursorByDate(cursorId, cursorDate))
                .orderBy(albumReviewStatisticsEntity.modifiedAt.desc())
                .limit(limit)
                .fetch().stream()
                .map(AlbumEntity::toModel)
                .toList();

        JPAQuery<Long> count = jpaQueryFactory.select(albumEntity.count())
                .from(albumEntity);

        Pageable pageable = PageRequest.ofSize(limit);

        return PageableExecutionUtils.getPage(albums, pageable, count::fetchOne);
    }

    @Override
    public Page<Album> findAllByAverageScore(String cursorId, String cursorScore, int limit) {

        List<Album> albums = jpaQueryFactory.select(albumEntity)
                .from(albumEntity)
                .innerJoin(albumEntity.statistics, albumReviewStatisticsEntity).fetchJoin()
                .where(cursorByAverage(cursorId, cursorScore))
                .orderBy(albumReviewStatisticsEntity.averageScore.desc())
                .limit(limit)
                .fetch().stream()
                .map(AlbumEntity::toModel)
                .toList();

        JPAQuery<Long> count = jpaQueryFactory.select(albumEntity.count())
                .from(albumEntity);

        Pageable pageable = PageRequest.ofSize(limit);

        return PageableExecutionUtils.getPage(albums, pageable, count::fetchOne);
    }

    @Override
    public Page<Album> findAllByReviewCount(String cursorId, String cursorReviewCount, int limit) {
        List<Album> albums = jpaQueryFactory.select(albumEntity)
                .from(albumEntity)
                .innerJoin(albumEntity.statistics, albumReviewStatisticsEntity).fetchJoin()
                .where(cursorByReviewCount(cursorId, cursorReviewCount))
                .orderBy(albumReviewStatisticsEntity.totalReviewer.desc())
                .limit(limit)
                .fetch().stream()
                .map(AlbumEntity::toModel)
                .toList();

        JPAQuery<Long> count = jpaQueryFactory.select(albumEntity.count())
                .from(albumEntity);

        Pageable pageable = PageRequest.ofSize(limit);

        return PageableExecutionUtils.getPage(albums, pageable, count::fetchOne);
    }
    private BooleanExpression cursorByReviewCount(String cursorId, String cursorReviewCount) {
        if (cursorId == null || cursorReviewCount == null) {
            return null;
        }
        int reviewCount = Integer.parseInt(cursorReviewCount);

        return albumReviewStatisticsEntity.totalReviewer.eq(reviewCount)
                .and(albumEntity.id.lt(cursorId))
                .or(albumReviewStatisticsEntity.totalReviewer.lt(reviewCount));
    }
    private BooleanExpression cursorByAverage(String cursorId, String cursorScore) {
        if (cursorId == null || cursorScore == null) {
            return null;
        }

        double score = Double.parseDouble(cursorScore);
        return albumReviewStatisticsEntity.averageScore.eq(score)
                .and(albumEntity.id.lt(cursorId))
                .or(albumReviewStatisticsEntity.averageScore.lt(score));
    }

    private BooleanExpression cursorByDate(String cursorId, String cursorDate) {
        if (cursorId == null || cursorDate == null) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.parse(cursorDate, formatter);

        return albumReviewStatisticsEntity.modifiedAt.eq(date)
                .and(albumEntity.id.lt(cursorId))
                .or(albumReviewStatisticsEntity.modifiedAt.lt(date));
    }
}
