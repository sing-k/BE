package com.project.singk.domain.album.service;

import com.project.singk.domain.album.controller.request.AlbumSort;
import com.project.singk.domain.album.infrastructure.spotify.*;
import com.project.singk.domain.album.service.port.*;
import lombok.Builder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.singk.domain.album.controller.port.AlbumService;
import com.project.singk.domain.album.controller.response.AlbumDetailResponse;
import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.album.controller.response.AlbumListResponse;
import com.project.singk.global.api.Page;

import lombok.RequiredArgsConstructor;

@Service
@Builder
@RequiredArgsConstructor
@Transactional
public class AlbumServiceImpl implements AlbumService {

	private final SpotifyRepository spotifyRepository;
	private final AlbumRepository albumRepository;
    private final TrackRepository trackRepository;
    private final ArtistRepository artistRepository;
    private final AlbumImageRepository albumImageRepository;

    @Override
	public AlbumDetailResponse getAlbum(String albumId) {
		Album album = albumRepository.findById(albumId).orElse(null);

		// DB에 데이터 있는 경우
		if (album != null) {
			return AlbumDetailResponse.from(album);
		}

		AlbumEntity spotifyAlbum = spotifyRepository.getAlbumById(albumId);
        album = albumRepository.save(spotifyAlbum.toModel());
		return AlbumDetailResponse.from(album);
	}

    @Override
	@Transactional(readOnly = true)
	public Page<AlbumListResponse> searchAlbums(String query, int offset, int limit) {
		// 앨범 목록 API 요청 준비

		Page<AlbumSimplifiedEntity> spotifyAlbums = spotifyRepository.searchAlbums(query, offset, limit);

		return Page.of(
                spotifyAlbums.getOffset(),
                spotifyAlbums.getLimit(),
                spotifyAlbums.getTotal(),
                spotifyAlbums.getItems().stream()
                        .map(album -> AlbumListResponse.from(album.toModel()))
                        .toList()

		);
	}

    @Override
    @Transactional(readOnly = true)
    public Page<AlbumListResponse> getPreviewAlbumsByAlbumSort(AlbumSort sort, int offset, int limit) {
        Page<Album> albums = albumRepository.findAllByAlbumSort(sort, offset, limit);
        return albums.map(AlbumListResponse::from);
    }

}
