package com.project.singk.domain.album.service;

import com.project.singk.domain.album.domain.*;
import com.project.singk.domain.album.infrastructure.spotify.*;
import com.project.singk.domain.album.service.port.*;
import com.project.singk.domain.review.domain.AlbumReviewStatistics;
import com.project.singk.global.api.CursorPageResponse;
import com.project.singk.global.api.OffsetPageResponse;
import lombok.Builder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.singk.domain.album.controller.port.AlbumService;
import com.project.singk.domain.album.controller.response.AlbumDetailResponse;
import com.project.singk.domain.album.controller.response.AlbumListResponse;

import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
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

        artists.addAll(album.getArtists().stream()
                .map(AlbumArtist::getArtist)
                .toList());


        for (Track track : album.getTracks()) {
            artists.addAll(track.getArtists().stream()
                    .map(TrackArtist::getArtist)
                    .toList());
        }

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
	public OffsetPageResponse<AlbumListResponse> searchAlbums(String query, int offset, int limit) {
		// 앨범 목록 API 요청 준비

		OffsetPageResponse<AlbumSimplifiedEntity> spotifyAlbums = spotifyRepository.searchAlbums(query, offset, limit);

		return OffsetPageResponse.of(
                spotifyAlbums.getOffset(),
                spotifyAlbums.getLimit(),
                spotifyAlbums.getTotal(),
                spotifyAlbums.getItems().stream()
                        .map(album -> AlbumListResponse.from(album.simplified(
                                albumRepository.getAlbumReviewStatisticsByAlbumId(album.getId())
                        )))
                        .toList()

		);
	}

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "albums_modified_at", key = "'albums_modified_at_'+ #cursorId + '_' + #cursorDate + '_' + #limit")
    public CursorPageResponse<AlbumListResponse> getAlbumsByDate(Long cursorId, String cursorDate, int limit) {
        List<AlbumSimplified> albums = albumRepository.findAllByModifiedAt(cursorId, cursorDate, limit);

        return CursorPageResponse.of(
                limit,
                albums.size() >= limit,
                albums.stream()
                        .map(AlbumListResponse::from)
                        .toList()
        );
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "albums_average_score", key = "'albums_average_score_'+ #cursorId + '_' + #cursorDate + '_' + #limit")
    public CursorPageResponse<AlbumListResponse> getAlbumsByAverageScore(Long cursorId, String cursorScore, int limit) {
        List<AlbumSimplified> albums = albumRepository.findAllByAverageScore(cursorId, cursorScore, limit);
        return CursorPageResponse.of(
                limit,
                albums.size() >= limit,
                albums.stream()
                        .map(AlbumListResponse::from)
                        .toList()
        );
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "albums_review_count", key = "'albums_review_count_'+ #cursorId + '_' + #cursorDate + '_' + #limit")
    public CursorPageResponse<AlbumListResponse> getAlbumsByReviewCount(Long cursorId, String cursorReviewCount, int limit) {
        List<AlbumSimplified> albums = albumRepository.findAllByReviewCount(cursorId, cursorReviewCount, limit);
        return CursorPageResponse.of(
                limit,
                albums.size() >= limit,
                albums.stream()
                        .map(AlbumListResponse::from)
                        .toList()
        );
    }

}
