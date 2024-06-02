package com.project.singk.domain.album.service;

import com.project.singk.domain.album.domain.AlbumImage;
import com.project.singk.domain.album.domain.Artist;
import com.project.singk.domain.album.domain.Track;
import com.project.singk.domain.album.infrastructure.spotify.*;
import com.project.singk.domain.album.service.port.*;
import lombok.Builder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.singk.domain.album.controller.port.AlbumService;
import com.project.singk.domain.album.controller.response.AlbumDetailResponse;
import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.album.controller.response.AlbumListResponse;
import com.project.singk.global.api.Page;

import lombok.RequiredArgsConstructor;

import java.util.List;

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

	public AlbumDetailResponse getAlbum(String albumId) {
        // TODO : Join ??
		Album album = albumRepository.findById(albumId).orElse(null);
        List<Track> tracks = trackRepository.findByAlbumId(albumId);
        List<Artist> artists = artistRepository.findByAlbumId(albumId);
        List<AlbumImage> images = albumImageRepository.findByAlbumId(albumId);

		// DB에 데이터 있는 경우
		if (album != null) {
			return AlbumDetailResponse.from(album, tracks, artists, images);
		}

		AlbumEntity spotifyAlbum = spotifyRepository.getAlbumById(albumId);
        album = albumRepository.save(spotifyAlbum.toModel());
        tracks = trackRepository.saveAll(spotifyAlbum.getTracks().stream()
                .map(track -> track.toModel(albumId))
                .toList());
        artists = artistRepository.saveAll(spotifyAlbum.getArtists().stream()
                .map(artist -> artist.toModel(albumId))
                .toList());
        images = albumImageRepository.saveAll(spotifyAlbum.getImages().stream()
                .map(image -> image.toModel(albumId))
                .toList());
		return AlbumDetailResponse.from(album, tracks, artists, images);
	}

	@Transactional(readOnly = true)
	public Page<AlbumListResponse> searchAlbums(String query, int offset, int limit) {
		// 앨범 목록 API 요청 준비

		Page<AlbumSimplifiedEntity> spotifyAlbums = spotifyRepository.searchAlbums(query, offset, limit);

		return Page.of(
                spotifyAlbums.getOffset(),
                spotifyAlbums.getLimit(),
                spotifyAlbums.getTotal(),
                spotifyAlbums.getItems().stream()
                        .map(album -> AlbumListResponse.from(
                                album.toModel(),
                                album.getArtists().stream()
                                .map(artist -> artist.toModel(album.getId()))
                                .toList(),
                                album.getImages().stream()
                                .map(image -> image.toModel(album.getId()))
                                .toList()))
                        .toList()

		);
	}
}
