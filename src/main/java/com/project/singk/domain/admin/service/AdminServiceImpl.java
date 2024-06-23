package com.project.singk.domain.admin.service;

import com.project.singk.domain.admin.controller.port.AdminService;
import com.project.singk.domain.album.controller.response.AlbumDetailResponse;
import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.album.domain.AlbumArtist;
import com.project.singk.domain.album.infrastructure.spotify.AlbumSimplifiedEntity;
import com.project.singk.domain.album.service.port.*;
import com.project.singk.domain.member.service.port.MemberRepository;
import com.project.singk.domain.review.domain.AlbumReviewStatistics;
import com.project.singk.global.api.PageResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Builder
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService {

    private final SpotifyRepository spotifyRepository;
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final MemberRepository memberRepository;

    @Override
    public PageResponse<AlbumDetailResponse> createAlbums(String query, int offset, int limit) {

        PageResponse<AlbumSimplifiedEntity> spotifyAlbums = spotifyRepository.searchAlbums(query, offset, limit);

        List<AlbumDetailResponse> albums = spotifyAlbums.getItems().stream()
                .map(spotifyAlbum -> {
                    Album album = spotifyRepository.getAlbumById(spotifyAlbum.getId()).toModel();

                    // 앨범 통게 생성
                    AlbumReviewStatistics statistics = AlbumReviewStatistics.empty();
                    album = album.updateStatistic(statistics);

                    if (albumRepository.existsById(spotifyAlbum.getId())) return AlbumDetailResponse.from(album);

                    // 아티스트 생성
                    for (AlbumArtist artist : album.getArtists()) {

                        if (!artistRepository.existById(artist.getArtist().getId())) {
                            artistRepository.save(artist.getArtist());
                        }
                    }

                    album = albumRepository.save(album);

                    return AlbumDetailResponse.from(album);
                })
                .toList();

        return PageResponse.of(
                offset,
                limit,
                spotifyAlbums.getTotal(),
                albums
        );
    }

    @Override
    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }
}
