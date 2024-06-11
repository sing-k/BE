package com.project.singk.domain.album.infrastructure;

import java.util.Optional;

import com.project.singk.domain.album.infrastructure.entity.*;
import com.project.singk.domain.album.infrastructure.jpa.AlbumJpaRepository;
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
}
