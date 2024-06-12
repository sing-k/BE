package com.project.singk.domain.album.infrastructure;

import java.util.List;
import java.util.Optional;

import com.project.singk.domain.album.controller.request.AlbumSort;
import com.project.singk.domain.album.infrastructure.entity.*;
import com.project.singk.domain.album.infrastructure.jpa.AlbumJpaRepository;
import com.project.singk.domain.review.controller.request.ReviewSort;
import com.project.singk.domain.review.infrastructure.QAlbumReviewEntity;
import com.project.singk.global.api.Page;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
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

        int count = jpaQueryFactory.select(albumEntity.count())
                .from(albumEntity)
                .fetchOne()
                .intValue();

        return Page.of(
                offset,
                limit,
                count,
                albums
        );
    }

    private OrderSpecifier createOrderSpecifier(AlbumSort sort) {
        return switch (sort) {
            case NEW -> new OrderSpecifier(Order.DESC, albumEntity.modifiedAt);
            case SCORES -> new OrderSpecifier(Order.DESC, albumEntity.totalScore);
            case REVIEWERS -> new OrderSpecifier(Order.DESC, albumEntity.totalReviewer);
        };
    }

}
