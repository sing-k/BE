package com.project.singk.domain.album.infrastructure;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import com.project.singk.domain.album.controller.request.AlbumSort;
import com.project.singk.domain.album.infrastructure.entity.*;
import com.project.singk.domain.album.infrastructure.jpa.AlbumJpaRepository;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
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

import static com.project.singk.domain.album.infrastructure.entity.QAlbumEntity.albumEntity;
import static com.project.singk.domain.album.infrastructure.entity.QAlbumImageEntity.albumImageEntity;
import static com.project.singk.domain.album.infrastructure.entity.QArtistEntity.artistEntity;
import static com.project.singk.domain.album.infrastructure.entity.QTrackEntity.trackEntity;

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
	public Album getById(String id) {
		return findById(id)
			.orElseThrow(() -> new ApiException(AppHttpStatus.NOT_FOUND_ALBUM));
	}

    @Override
	public Optional<Album> findById(String id) {
        return Optional.ofNullable(jpaQueryFactory
                .select(albumEntity)
                .from(albumEntity)
                .where(albumEntity.id.eq(id))
                .innerJoin(albumEntity.tracks, trackEntity)
                .innerJoin(albumEntity.artists, artistEntity)
                .innerJoin(albumEntity.images, albumImageEntity)
                .fetchOne())
                .map(AlbumEntity::toModel);
	}

    @Override
    public Page<Album> findAllByAlbumSort(AlbumSort sort, int offset, int limit) {
        List<Album> albums = jpaQueryFactory.select(albumEntity)
                .from(albumEntity)
                .orderBy(createOrderSpecifier(sort))
                .offset(offset)
                .limit(limit)
                .fetch().stream()
                .map(AlbumEntity::toModel)
                .toList();

        JPAQuery<Long> count = jpaQueryFactory.select(albumEntity.count())
                .from(albumEntity);

        Pageable pageable = PageRequest.of(offset / limit, limit);

        return PageableExecutionUtils.getPage(albums, pageable, count::fetchOne);
    }

    @Override
    public Page<Album> findAllByModifiedAt(String cursorId, String cursorDate, int limit) {
        List<Album> albums = jpaQueryFactory.select(albumEntity)
                .from(albumEntity)
                .where(cursorByDate(cursorId, cursorDate))
                .orderBy(albumEntity.modifiedAt.desc())
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

        NumberTemplate<Double> averageScore = Expressions.numberTemplate(Double.class,
                "CAST(ROUND(CASE WHEN {1} = 0 THEN NULL ELSE {0} / {1} END, 2) AS DOUBLE)", albumEntity.totalScore, albumEntity.totalReviewer);

        List<Album> albums = jpaQueryFactory.select(albumEntity)
                .from(albumEntity)
                .where(cursorByAverage(cursorId, cursorScore, averageScore))
                .orderBy(averageScore.desc().nullsLast())
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
                .where(cursorByReviewCount(cursorId, cursorReviewCount))
                .orderBy(albumEntity.totalReviewer.desc())
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
        long reviewCount = Long.parseLong(cursorReviewCount);

        return albumEntity.totalReviewer.eq(reviewCount)
                .and(albumEntity.id.ne(cursorId))
                .or(albumEntity.totalReviewer.lt(reviewCount));
    }
    private BooleanExpression cursorByAverage(String cursorId, String cursorScore, NumberTemplate<Double> averageScore) {
        if (cursorId == null || cursorScore == null) {
            return null;
        }

        double score = Double.parseDouble(cursorScore);
        return averageScore.eq(score)
                .and(albumEntity.id.ne(cursorId))
                .or(averageScore.lt(score));
    }

    private BooleanExpression cursorByDate(String cursorId, String cursorDate) {
        if (cursorId == null || cursorDate == null) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.parse(cursorDate, formatter);

        return albumEntity.modifiedAt.eq(date)
                .and(albumEntity.id.ne(cursorId))
                .or(albumEntity.modifiedAt.lt(date));
    }

    private OrderSpecifier createOrderSpecifier(AlbumSort sort) {
        return switch (sort) {
            case NEW -> new OrderSpecifier(Order.DESC, albumEntity.modifiedAt);
            case SCORES -> new OrderSpecifier(Order.DESC, albumEntity.totalScore);
            case REVIEWERS -> new OrderSpecifier(Order.DESC, albumEntity.totalReviewer);
        };
    }

}
