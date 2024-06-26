package com.project.singk.domain.album.service;

import com.project.singk.domain.album.domain.*;
import com.project.singk.domain.album.infrastructure.spotify.*;
import com.project.singk.domain.album.service.port.*;
import com.project.singk.domain.review.domain.AlbumReviewStatistics;
import com.project.singk.global.api.PageResponse;
import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.singk.domain.album.controller.port.AlbumService;
import com.project.singk.domain.album.controller.response.AlbumDetailResponse;
import com.project.singk.domain.album.controller.response.AlbumListResponse;

import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

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

        // 앨범 통계 생성
        AlbumReviewStatistics statistics = AlbumReviewStatistics.empty();
        album = spotifyAlbum.toModel();

        album = album.updateStatistic(statistics);

        // 아티스트 생성
        Set<Artist> artists = new HashSet<>();
        for (Track track : album.getTracks()) {
            artists.addAll(track.getArtists().stream()
                    .map(TrackArtist::getArtist)
                    .toList());
        }

        System.out.println(artists.size());

        for (Artist artist : artists) {
            if (!artistRepository.existById(artist.getId())) {
                artistRepository.save(artist);
            }
        }

        album = albumRepository.save(album);

		return AlbumDetailResponse.from(album);
	}

    @Override
	@Transactional(readOnly = true)
	public PageResponse<AlbumListResponse> searchAlbums(String query, int offset, int limit) {
		// 앨범 목록 API 요청 준비

		PageResponse<AlbumSimplifiedEntity> spotifyAlbums = spotifyRepository.searchAlbums(query, offset, limit);

		return PageResponse.of(
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
    public PageResponse<AlbumListResponse> getAlbumsByDate(String cursorId, String cursorDate, int limit) {
        Page<Album> albums = albumRepository.findAllByModifiedAt(cursorId, cursorDate, limit);
        return PageResponse.of(
                (int) albums.getPageable().getOffset(),
                limit,
                (int) albums.getTotalElements(),
                albums.stream()
                        .map(AlbumListResponse::from)
                        .toList()
        );
    }

    @Override
    public PageResponse<AlbumListResponse> getAlbumsByAverageScore(String cursorId, String cursorScore, int limit) {
        Page<Album> albums = albumRepository.findAllByAverageScore(cursorId, cursorScore, limit);
        return PageResponse.of(
                (int) albums.getPageable().getOffset(),
                limit,
                (int) albums.getTotalElements(),
                albums.stream()
                        .map(AlbumListResponse::from)
                        .toList()
        );
    }

    @Override
    public PageResponse<AlbumListResponse> getAlbumsByReviewCount(String cursorId, String cursorReviewCount, int limit) {
        Page<Album> albums = albumRepository.findAllByReviewCount(cursorId, cursorReviewCount, limit);
        return PageResponse.of(
                (int) albums.getPageable().getOffset(),
                limit,
                (int) albums.getTotalElements(),
                albums.stream()
                        .map(AlbumListResponse::from)
                        .toList()
        );
    }

}
