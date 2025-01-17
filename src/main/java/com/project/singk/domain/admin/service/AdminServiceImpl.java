package com.project.singk.domain.admin.service;

import com.project.singk.domain.activity.controller.response.ActivityHistoryResponse;
import com.project.singk.domain.activity.domain.ActivityHistory;
import com.project.singk.domain.activity.domain.ActivityType;
import com.project.singk.domain.activity.infrastructure.ActivityHistoryEntity;
import com.project.singk.domain.activity.service.port.ActivityHistoryRepository;
import com.project.singk.domain.admin.controller.port.AdminService;
import com.project.singk.domain.album.controller.response.AlbumDetailResponse;
import com.project.singk.domain.album.controller.response.AlbumListResponse;
import com.project.singk.domain.album.domain.*;
import com.project.singk.domain.album.infrastructure.spotify.AlbumSimplifiedEntity;
import com.project.singk.domain.album.service.port.*;
import com.project.singk.domain.common.service.port.RandomHolder;
import com.project.singk.domain.common.service.port.S3Repository;
import com.project.singk.domain.member.controller.response.MemberResponse;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.service.port.MemberRepository;
import com.project.singk.domain.review.domain.AlbumReviewStatistics;
import com.project.singk.global.api.CursorPageResponse;
import com.project.singk.global.api.OffsetPageResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

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
    private final RandomHolder randomHolder;
    private final ActivityHistoryRepository activityHistoryRepository;
    @Override
    public OffsetPageResponse<AlbumDetailResponse> createAlbums(String query, int offset, int limit) {

        OffsetPageResponse<AlbumSimplifiedEntity> spotifyAlbums = spotifyRepository.searchAlbums(query, offset, limit);

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

        return OffsetPageResponse.of(
                offset,
                limit,
                spotifyAlbums.getTotal(),
                albums
        );
    }

    @Override
    @Async
    public CompletableFuture<OffsetPageResponse<AlbumDetailResponse>> createAlbumsWithAsync(String query, int offset, int limit) {

        // 외부 API 앨범 목록 조회 요청
        OffsetPageResponse<AlbumSimplifiedEntity> spotifyAlbums = spotifyRepository.searchAlbums(query, offset, limit);

        // 비동기 작업
        List<CompletableFuture<AlbumDetailResponse>> futures = spotifyAlbums.getItems().stream()

                // 외부 API 앨범 상세 조회 요청 (비동기)
                .map(spotifyAlbum -> spotifyRepository.getAlbumByIdWithAsync(spotifyAlbum.getId())

                        // 앨범 저장
                        .thenApply(albumEntity -> {
                            Album album = albumEntity.toModel();

                            // 앨범 통계 생성
                            AlbumReviewStatistics statistics = AlbumReviewStatistics.empty();
                            album = album.updateStatistic(statistics);

                            // 이미 DB에 존재하는 경우 리턴
                            if (albumRepository.existsById(spotifyAlbum.getId())) {
                                return AlbumDetailResponse.from(album);
                            }

                            // 아티스트 저장
                            Set<Artist> artists = new HashSet<>(album.getArtists().stream()
                                    .map(AlbumArtist::getArtist)
                                    .toList());

                            album.getTracks()
                                    .forEach(track -> artists.addAll(track.getArtists().stream()
                                            .map(TrackArtist::getArtist)
                                            .toList()));

                            artists.forEach(artist -> {
                                if (!artistRepository.existById(artist.getId())) {
                                    artistRepository.save(artist);
                                }
                            });

                            album = albumRepository.save(album);

                            return AlbumDetailResponse.from(album);
                        })
                ).toList();

        // 모든 비동기 작업이 완료될 때까지 기다림
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        return CompletableFuture.completedFuture(
                OffsetPageResponse.of(
                    offset,
                    limit,
                    spotifyAlbums.getTotal(),
                    futures.stream()
                        .map(CompletableFuture::join)
                        .toList()
        ));
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

    @Override
    @Transactional(readOnly = true)
    public List<AlbumListResponse> getAlbums() {
        return albumRepository.findAll().stream()
                .map(AlbumListResponse::from)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OffsetPageResponse<AlbumListResponse> getAlbumsWithOffsetPaging(int offset, int limit) {
        Page<AlbumSimplified> albums = albumRepository.findAllWithOffsetPaging(offset, limit);

        return OffsetPageResponse.of(
                offset,
                limit,
                (int) albums.getTotalElements(),
                albums.stream()
                        .map(AlbumListResponse::from)
                        .toList()
        );
    }
    @Override
    @Transactional(readOnly = true)
    public CursorPageResponse<AlbumListResponse> getAlbumsWithCursorPaging(Long cursorId, String cursorDate, int limit) {
        List<AlbumSimplified> albums = albumRepository.findAllWithCursorPaging(cursorId, cursorDate, limit);

        return CursorPageResponse.of(
                limit,
                albums.size() >= limit,
                albums.stream()
                        .map(AlbumListResponse::from)
                        .toList()
        );
    }

    @Override
    public List<ActivityHistoryResponse> createActivityHistories(Long memberId, String startDate, String endDate, int count) {
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Member member = memberRepository.getById(memberId);

        List<LocalDateTime> dateTimes = start.datesUntil(end.plusDays(1))
                .map(date -> date.atStartOfDay().plusHours(randomHolder.randomInt(12) + 1))
                .toList();
        ActivityType[] activityTypes = ActivityType.values();

        List<ActivityHistory> activityHistories = new ArrayList<>();
        for (LocalDateTime dateTime : dateTimes) {
            for (int i = 0; i < count; i++) {
                ActivityType type = activityTypes[randomHolder.randomInt(activityTypes.length)];
                activityHistories.add(
                        ActivityHistory.builder()
                                .type(type)
                                .score(type.getScore())
                                .member(member)
                                .createdAt(dateTime)
                                .build()
                );
            }
        }

        ActivityHistoryEntity entity = ActivityHistoryEntity.from(activityHistories.get(0));

        return activityHistoryRepository.saveAll(activityHistories).stream()
                .map(ActivityHistoryResponse::from)
                .toList();
    }
}
