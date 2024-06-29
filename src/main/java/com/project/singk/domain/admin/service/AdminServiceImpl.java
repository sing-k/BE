package com.project.singk.domain.admin.service;

import com.project.singk.domain.admin.controller.port.AdminService;
import com.project.singk.domain.album.controller.response.AlbumDetailResponse;
import com.project.singk.domain.album.domain.*;
import com.project.singk.domain.album.infrastructure.spotify.AlbumSimplifiedEntity;
import com.project.singk.domain.album.service.port.*;
import com.project.singk.domain.common.service.port.S3Repository;
import com.project.singk.domain.member.controller.response.MemberResponse;
import com.project.singk.domain.member.service.port.MemberRepository;
import com.project.singk.domain.review.domain.AlbumReviewStatistics;
import com.project.singk.global.api.PageResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Builder
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService {

    private final SpotifyRepository spotifyRepository;
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final MemberRepository memberRepository;
    private final S3Repository s3Repository;

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

    @Override
    public List<MemberResponse> getMembers() {
        return memberRepository.findAll().stream()
                .map(member -> {
                    String imageUrl = s3Repository.getPreSignedGetUrl(member.getImageUrl());
                    return MemberResponse.from(member, imageUrl);
                })
                .toList();
    }
}
