package com.project.singk.domain.album.infrastructure;

import java.util.Optional;

import com.project.singk.domain.album.infrastructure.entity.AlbumEntity;
import com.project.singk.domain.album.infrastructure.jpa.AlbumJpaRepository;
import org.springframework.stereotype.Repository;

import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.album.service.port.AlbumRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AlbumRepositoryImpl implements AlbumRepository {
	private final AlbumJpaRepository albumJpaRepository;

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
		return albumJpaRepository.findById(id).map(AlbumEntity::toModel);
	}
}
