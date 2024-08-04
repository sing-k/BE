package com.project.singk.domain.album.infrastructure;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import com.project.singk.domain.album.domain.AlbumSimplified;
import com.project.singk.domain.album.infrastructure.entity.*;
import com.project.singk.domain.album.infrastructure.jpa.AlbumJpaRepository;
import com.project.singk.domain.review.domain.AlbumReviewStatistics;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final JPAQueryFactory queryFactory;

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
    public List<AlbumSimplified> findAll() {
        return queryFactory.selectFrom(albumEntity)
                .innerJoin(albumEntity.statistics, albumReviewStatisticsEntity).fetchJoin()
                .fetch().stream()
                .map(AlbumEntity::simplified)
                .toList();
    }

    @Override
    public Page<AlbumSimplified> findAllWithOffsetPaging(int offset, int limit) {
        List<AlbumSimplified> albums = queryFactory.selectFrom(albumEntity)
                .innerJoin(albumEntity.statistics, albumReviewStatisticsEntity).fetchJoin()
                .offset(offset)
                .limit(limit)
                .orderBy(albumReviewStatisticsEntity.modifiedAt.desc())
                .fetch().stream()
                .map(AlbumEntity::simplified)
                .toList();

        Long count = queryFactory.select(albumEntity.count())
                .from(albumEntity)
                .fetchOne();

        Pageable pageable = PageRequest.ofSize(limit);

        return new PageImpl<>(albums, pageable, count);
    }

    @Override
    public List<AlbumSimplified> findAllWithCursorPaging(Long cursorId, String cursorDate, int limit) {
        return queryFactory.selectFrom(albumEntity)
                .innerJoin(albumEntity.statistics, albumReviewStatisticsEntity).fetchJoin()
                .where(cursorByDate(cursorId, cursorDate))
                .limit(limit)
                .orderBy(albumReviewStatisticsEntity.modifiedAt.desc())
                .fetch().stream()
                .map(AlbumEntity::simplified)
                .toList();
    }

    @Override
    public Album getByIdWithStatistics(String albumId) {
        return findByIdWithStatistics(albumId)
                .orElseThrow(() -> new ApiException(AppHttpStatus.NOT_FOUND_ALBUM));
    }

    @Override
    public AlbumReviewStatistics getAlbumReviewStatisticsByAlbumId(String albumId) {

        return queryFactory.select(albumReviewStatisticsEntity)
                .from(albumEntity)
                .innerJoin(albumEntity.statistics, albumReviewStatisticsEntity)
                .where(albumEntity.id.eq(albumId))
                .fetchOne()
                .toModel();
    }

    @Override
    public Optional<Album> findByIdWithStatistics(String albumId) {
        return Optional.ofNullable(queryFactory.select(albumEntity)
                .from(albumEntity)
                .where(albumEntity.id.eq(albumId))
                .innerJoin(albumEntity.statistics, albumReviewStatisticsEntity).fetchJoin()
                .fetchOne())
                .map(AlbumEntity::toModel);
    }

    @Override
	public Optional<Album> findById(String id) {
        return Optional.ofNullable(queryFactory
                .selectFrom(albumEntity)
                .where(albumEntity.id.eq(id))
                .leftJoin(albumEntity.tracks, trackEntity)
                .leftJoin(albumEntity.images, albumImageEntity)
                .leftJoin(albumEntity.artists, albumArtistEntity)
                .fetchOne())
                .map(AlbumEntity::toModel);
	}

    @Override
    public List<AlbumSimplified> findAllByModifiedAt(Long cursorId, String cursorDate, int limit) {
        return queryFactory.selectFrom(albumEntity)
                .innerJoin(albumEntity.statistics, albumReviewStatisticsEntity).fetchJoin()
                .where(cursorByDate(cursorId, cursorDate))
                .orderBy(albumReviewStatisticsEntity.modifiedAt.desc())
                .limit(limit)
                .fetch().stream()
                .map(AlbumEntity::simplified)
                .toList();
    }

    private BooleanExpression cursorByDate(Long cursorId, String cursorDate) {
        if (cursorId == null || cursorDate == null) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.parse(cursorDate, formatter);

        return albumReviewStatisticsEntity.modifiedAt.eq(date)
                .and(albumReviewStatisticsEntity.id.gt(cursorId))
                .or(albumReviewStatisticsEntity.modifiedAt.lt(date));
    }

    @Override
    public List<AlbumSimplified> findAllByAverageScore(Long cursorId, String cursorScore, int limit) {
        return queryFactory.select(albumEntity)
                .from(albumEntity)
                .innerJoin(albumEntity.statistics, albumReviewStatisticsEntity).fetchJoin()
                .where(cursorByAverage(cursorId, cursorScore))
                .orderBy(albumReviewStatisticsEntity.averageScore.desc())
                .limit(limit)
                .fetch().stream()
                .map(AlbumEntity::simplified)
                .toList();
    }

    private BooleanExpression cursorByAverage(Long cursorId, String cursorScore) {
        if (cursorId == null || cursorScore == null) {
            return null;
        }

        double score = Double.parseDouble(cursorScore);

        return albumReviewStatisticsEntity.averageScore.eq(score)
                .and(albumReviewStatisticsEntity.id.gt(cursorId))
                .or(albumReviewStatisticsEntity.averageScore.lt(score));
    }

    @Override
    public List<AlbumSimplified> findAllByReviewCount(Long cursorId, String cursorReviewCount, int limit) {
        return queryFactory.select(albumEntity)
                .from(albumEntity)
                .innerJoin(albumEntity.statistics, albumReviewStatisticsEntity).fetchJoin()
                .where(cursorByReviewCount(cursorId, cursorReviewCount))
                .orderBy(albumReviewStatisticsEntity.totalReviewer.desc(), albumEntity.id.asc())
                .limit(limit)
                .fetch().stream()
                .map(AlbumEntity::simplified)
                .toList();
    }
    private BooleanExpression cursorByReviewCount(Long cursorId, String cursorReviewCount) {
        if (cursorId == null || cursorReviewCount == null) {
            return null;
        }
        int reviewCount = Integer.parseInt(cursorReviewCount);

        return albumReviewStatisticsEntity.totalReviewer.eq(reviewCount)
                .and(albumReviewStatisticsEntity.id.gt(cursorId))
                .or(albumReviewStatisticsEntity.totalReviewer.lt(reviewCount));
    }

}
