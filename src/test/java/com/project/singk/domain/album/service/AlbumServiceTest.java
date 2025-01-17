package com.project.singk.domain.album.service;

import com.project.singk.domain.album.controller.response.AlbumDetailResponse;
import com.project.singk.domain.album.controller.response.AlbumListResponse;
import com.project.singk.domain.album.domain.*;
import com.project.singk.domain.album.infrastructure.spotify.AlbumEntity;
import com.project.singk.domain.review.domain.AlbumReviewStatistics;
import com.project.singk.global.api.CursorPageResponse;
import com.project.singk.global.api.OffsetPageResponse;
import com.project.singk.mock.TestContainer;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AlbumServiceTest {

    /**
     * getAlbum
     */
    @Test
    public void 앨범을_상세조회할_때_DB에_해당_앨범이_존재하지_않으면_Spotify_API를_사용하여_데이터를_가져온다() {
        // given
        TestContainer testContainer = TestContainer.builder().build();
        AlbumEntity spotifyEntity = testContainer.spotifyRepository.getAlbumById("id");

        // when
        AlbumDetailResponse response = testContainer.albumService.getAlbum("id");

        // then
        assertAll(
                () -> assertThat(response.getId()).isEqualTo(spotifyEntity.getId()),
                () -> assertThat(response.getName()).isEqualTo(spotifyEntity.getName()),
                () -> assertThat(response.getType()).isEqualTo(AlbumType.of(spotifyEntity.getType(), spotifyEntity.getTracks().size()).getName()),
                () -> assertThat(response.getReleasedAt()).isEqualTo(spotifyEntity.getReleasedAt()),
                () -> assertThat(response.getTrackCount()).isEqualTo(spotifyEntity.getTracks().size()),
                () -> assertThat(response.getTracks().get(0).getId()).isEqualTo(spotifyEntity.getTracks().get(0).getId()),
                () -> assertThat(response.getArtists().get(0).getId()).isEqualTo(spotifyEntity.getArtists().get(0).getId()),
                () -> assertThat(response.getImages().get(0).getImageUrl()).isEqualTo(spotifyEntity.getImages().get(0).getImageUrl())
        );
    }

    @Test
    public void 앨범을_상세조회할_때_DB에_해당_앨범이_존재하는_경우_DB_데이터를_가져온다() {
        // given
        TestContainer testContainer = TestContainer.builder().build();

        List<Artist> artists = List.of(
                Artist.builder()
                        .name("NewJeans")
                        .build()
        );

        List<Track> tracks = List.of(
                Track.builder()
                        .id("1")
                        .name("Bubble Gum")
                        .artists(artists.stream()
                                .map(TrackArtist::from)
                                .toList())
                        .build(),
                Track.builder()
                        .id("2")
                        .name("How Sweet")
                        .artists(artists.stream()
                                .map(TrackArtist::from)
                                .toList())
                        .build(),
                Track.builder()
                        .id("3")
                        .name("How Sweet (Instrumental)")
                        .artists(artists.stream()
                                .map(TrackArtist::from)
                                .toList())
                        .build(),
                Track.builder()
                        .id("4")
                        .name("Bubble Gum (Instrumental)")
                        .artists(artists.stream()
                                .map(TrackArtist::from)
                                .toList())
                        .build()
        );

        List<AlbumImage> images = List.of(
                AlbumImage.builder()
                        .imageUrl("https://i.scdn.co/image/ab67616d0000b273b657fbb27b17e7bd4691c2b2")
                        .build()
        );
        Album album = Album.builder()
                .id("0EhZEM4RRz0yioTgucDhJq")
                .name("How Sweet")
                .type(AlbumType.EP)
                .releasedAt(LocalDateTime.of(2024, 5, 24, 0, 0, 0))
                .tracks(tracks)
                .artists(artists.stream()
                        .map(AlbumArtist::from)
                        .toList())
                .images(images)
                .statistics(AlbumReviewStatistics.empty())
                .build();

        testContainer.albumRepository.save(album);

        // when
        AlbumDetailResponse response = testContainer.albumService.getAlbum("0EhZEM4RRz0yioTgucDhJq");

        // then
        assertAll(
                () -> assertThat(response.getId()).isEqualTo("0EhZEM4RRz0yioTgucDhJq"),
                () -> assertThat(response.getName()).isEqualTo("How Sweet"),
                () -> assertThat(response.getType()).isEqualTo(AlbumType.EP.getName()),
                () -> assertThat(response.getReleasedAt()).isEqualTo(LocalDateTime.of(2024, 5,24, 0, 0,0)),
                () -> assertThat(response.getTrackCount()).isEqualTo(4),
                () -> assertThat(response.getTracks().get(0).getName()).isEqualTo("Bubble Gum"),
                () -> assertThat(response.getArtists().get(0).getName()).isEqualTo("NewJeans"),
                () -> assertThat(response.getImages().get(0).getImageUrl()).isEqualTo("https://i.scdn.co/image/ab67616d0000b273b657fbb27b17e7bd4691c2b2")
        );
    }

    /**
     * searchAlbums
     */
    @Test
    public void 앨범을_검색할_수_있다() {
        // given
        TestContainer testContainer = TestContainer.builder().build();

        // when
        OffsetPageResponse<AlbumListResponse> response = testContainer.albumService.searchAlbums("query", 0, 20);

        // then
        assertAll(
                () -> assertThat(response.getOffset()).isEqualTo(0),
                () -> assertThat(response.getLimit()).isEqualTo(20),
                () -> assertThat(response.getTotal()).isEqualTo(1),
                () -> assertThat(response.getItems().get(0).getName()).isEqualTo("How Sweet")
        );
    }

    /**
     * getAlbumsByDate
     */
    @Test
    public void CusorId와_CursorDate가_주어지지_않았다면_최근_평가된_앨범의_첫번째_페이지를_반환한다() {
        // given
        List<Integer> totalReviewer = List.of(10, 8, 12, 15, 5, 20, 8, 18, 10, 14);
        List<Integer> totalScore = List.of(35, 25, 50, 55, 15, 90, 28, 75, 32, 60);

        List<Album> albums = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            AlbumReviewStatistics statistics = AlbumReviewStatistics.builder()
                    .id((long) i)
                    .totalReviewer(totalReviewer.get(i - 1))
                    .totalScore(totalScore.get(i - 1))
                    .modifiedAt(LocalDateTime.of(2024, 6, i, 0, 0, 0))
                    .build();

            albums.add(Album.builder()
                    .id("cursor-id" + i)
                    .tracks(new ArrayList<>())
                    .artists(new ArrayList<>())
                    .images(new ArrayList<>())
                    .statistics(statistics)
                    .build()
            );
        }
        TestContainer testContainer = TestContainer.builder().build();
        albums = testContainer.albumRepository.saveAll(albums);

        Long cursorId = null;
        String cursorDate = null;
        int limit = 5;
        // when
        CursorPageResponse<AlbumListResponse> response = testContainer.albumService.getAlbumsByDate(cursorId, cursorDate, limit);

        // then
        assertAll(
                () -> assertThat(response.getLimit()).isEqualTo(5),
                () -> assertThat(response.isHasNext()).isEqualTo(true),
                () -> assertThat(response.getItems().size()).isEqualTo(5),
                () -> assertThat(response.getItems().get(0).getId()).isEqualTo("cursor-id10"),
                () -> assertThat(response.getItems().get(4).getId()).isEqualTo("cursor-id6")
        );
    }
    @Test
    public void CusorId와_CursorDate가_주어졌을_때_최근_평가된_앨범의_해당하는_페이지를_반환한다() {
        // given
        List<Integer> totalReviewer = List.of(10, 8, 12, 15, 5, 20, 8, 18, 10, 14);
        List<Integer> totalScore = List.of(35, 25, 50, 55, 15, 90, 28, 75, 32, 60);
        List<Album> albums = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            AlbumReviewStatistics statistics = AlbumReviewStatistics.builder()
                    .id((long) i)
                    .totalReviewer(totalReviewer.get(i - 1))
                    .totalScore(totalScore.get(i - 1))
                    .modifiedAt(LocalDateTime.of(2024, 6, i, 0, 0, 0))
                    .build();

            albums.add(Album.builder()
                    .id("cursor-id" + i)
                    .tracks(new ArrayList<>())
                    .artists(new ArrayList<>())
                    .images(new ArrayList<>())
                    .statistics(statistics)
                    .build()
            );
        }
        TestContainer testContainer = TestContainer.builder().build();
        albums = testContainer.albumRepository.saveAll(albums);

        Long cursorId = 7L;
        String cursorDate = "2024-06-04 00:00:00";
        int limit = 5;

        // when
        CursorPageResponse<AlbumListResponse> response = testContainer.albumService.getAlbumsByDate(cursorId, cursorDate, limit);

        // then
        assertAll(
                () -> assertThat(response.getLimit()).isEqualTo(5),
                () -> assertThat(response.isHasNext()).isEqualTo(false),
                () -> assertThat(response.getItems().size()).isEqualTo(4),
                () -> assertThat(response.getItems().get(0).getId()).isEqualTo("cursor-id4"),
                () -> assertThat(response.getItems().get(3).getId()).isEqualTo("cursor-id1")
        );
    }

    /**
     * getAlbumsByAverageScore
     */
    @Test
    public void CusorId와_CursorScore가_주어지지_않았다면_높은_평점_앨범의_첫번째_페이지를_반환한다() {
        // given
        List<Integer> totalReviewer = List.of(10, 8, 12, 15, 5, 20, 8, 18, 10, 14);
        List<Integer> totalScore = List.of(36, 25, 50, 55, 15, 90, 28, 75, 32, 60);
        List<Double> totalAverage = List.of(3.6, 3.12, 4.17, 3.67, 3.0, 4.5, 3.5, 4.17, 3.2, 4.23);
        List<Album> albums = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            int reviewer = totalReviewer.get(i - 1);
            int score = totalScore.get(i - 1);
            double average = totalAverage.get(i - 1);

            AlbumReviewStatistics statistics = AlbumReviewStatistics.builder()
                    .id((long) i)
                    .totalReviewer(reviewer)
                    .totalScore(score)
                    .averageScore(average)
                    .modifiedAt(LocalDateTime.of(2024, 6, i, 0, 0, 0))
                    .build();

            albums.add(Album.builder()
                    .id("cursor-id" + i)
                    .tracks(new ArrayList<>())
                    .artists(new ArrayList<>())
                    .images(new ArrayList<>())
                    .statistics(statistics)
                    .build()
            );
        }
        TestContainer testContainer = TestContainer.builder().build();
        albums = testContainer.albumRepository.saveAll(albums);

        Long cursorId = null;
        String cursorDate = null;
        int limit = 5;

        // when
        CursorPageResponse<AlbumListResponse> response = testContainer.albumService.getAlbumsByAverageScore(cursorId, cursorDate, limit);

        // then
        assertAll(
                () -> assertThat(response.getLimit()).isEqualTo(5),
                () -> assertThat(response.isHasNext()).isEqualTo(true),
                () -> assertThat(response.getItems().size()).isEqualTo(5),
                () -> assertThat(response.getItems().get(0).getId()).isEqualTo("cursor-id6"),
                () -> assertThat(response.getItems().get(4).getId()).isEqualTo("cursor-id4")
        );
    }
    @Test
    public void CusorId와_CursorScore가_주어졌을_때_높은_평점_앨범의_해당하는_페이지를_반환한다() {
        // given
        List<Integer> totalReviewer = List.of(10, 8, 12, 15, 5, 20, 8, 18, 10, 14);
        List<Integer> totalScore = List.of(36, 25, 50, 55, 15, 90, 28, 75, 32, 60);
        List<Double> totalAverage = List.of(3.6, 3.12, 4.17, 3.67, 3.0, 4.5, 3.5, 4.17, 3.2, 4.23);
        List<Album> albums = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            int reviewer = totalReviewer.get(i - 1);
            int score = totalScore.get(i - 1);
            double average = totalAverage.get(i - 1);

            AlbumReviewStatistics statistics = AlbumReviewStatistics.builder()
                    .id((long) i)
                    .totalReviewer(reviewer)
                    .totalScore(score)
                    .averageScore(average)
                    .modifiedAt(LocalDateTime.of(2024, 6, i, 0, 0, 0))
                    .build();

            albums.add(Album.builder()
                    .id("cursor-id" + i)
                    .tracks(new ArrayList<>())
                    .artists(new ArrayList<>())
                    .images(new ArrayList<>())
                    .statistics(statistics)
                    .build()
            );
        }
        TestContainer testContainer = TestContainer.builder().build();
        albums = testContainer.albumRepository.saveAll(albums);

        Long cursorId = 1L;
        String cursorScore = "3.6";
        int limit = 5;

        // when
        CursorPageResponse<AlbumListResponse> response = testContainer.albumService.getAlbumsByAverageScore(cursorId, cursorScore, limit);

        // then
        assertAll(
                () -> assertThat(response.getLimit()).isEqualTo(5),
                () -> assertThat(response.isHasNext()).isEqualTo(false),
                () -> assertThat(response.getItems().size()).isEqualTo(4),
                () -> assertThat(response.getItems().get(0).getId()).isEqualTo("cursor-id7"),
                () -> assertThat(response.getItems().get(3).getId()).isEqualTo("cursor-id5")
        );
    }

    /**
     * getAlbumsByReviewCount
     */
    @Test
    public void CusorId와_CursorReviewCount가_주어지지_않았다면_높은_참여자_수_앨범의_첫번째_페이지를_반환한다() {
        // given
        List<Integer> totalReviewer = List.of(10, 8, 12, 15, 5, 20, 7, 18, 9, 14);
        List<Integer> totalScore = List.of(35, 25, 50, 55, 15, 90, 28, 75, 32, 60);
        List<Album> albums = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            AlbumReviewStatistics statistics = AlbumReviewStatistics.builder()
                    .id((long) i)
                    .totalReviewer(totalReviewer.get(i - 1))
                    .totalScore(totalScore.get(i - 1))
                    .modifiedAt(LocalDateTime.of(2024, 6, i, 0, 0, 0))
                    .build();

            albums.add(Album.builder()
                    .id("cursor-id" + i)
                    .tracks(new ArrayList<>())
                    .artists(new ArrayList<>())
                    .images(new ArrayList<>())
                    .statistics(statistics)
                    .build()
            );
        }
        TestContainer testContainer = TestContainer.builder().build();
        albums = testContainer.albumRepository.saveAll(albums);

        Long cursorId = null;
        String cursorReviewCount = null;
        int limit = 5;

        // when
        CursorPageResponse<AlbumListResponse> response = testContainer.albumService.getAlbumsByReviewCount(cursorId, cursorReviewCount, limit);

        // then
        assertAll(
                () -> assertThat(response.getLimit()).isEqualTo(5),
                () -> assertThat(response.isHasNext()).isEqualTo(true),
                () -> assertThat(response.getItems().size()).isEqualTo(5),
                () -> assertThat(response.getItems().get(0).getId()).isEqualTo("cursor-id6"),
                () -> assertThat(response.getItems().get(4).getId()).isEqualTo("cursor-id3")
        );
    }
    @Test
    public void CusorId와_CursorReviewCount가_주어졌을_때_높은_참여자_수의_해당하는_페이지를_반환한다() {
        // given
        List<Integer> totalReviewer = List.of(10, 8, 12, 15, 5, 20, 7, 18, 9, 14);
        List<Integer> totalScore = List.of(35, 25, 50, 55, 15, 90, 28, 75, 32, 60);
        List<Album> albums = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            AlbumReviewStatistics statistics = AlbumReviewStatistics.builder()
                    .id((long) i)
                    .totalReviewer(totalReviewer.get(i - 1))
                    .totalScore(totalScore.get(i - 1))
                    .modifiedAt(LocalDateTime.of(2024, 6, i, 0, 0, 0))
                    .build();

            albums.add(Album.builder()
                    .id("cursor-id" + i)
                    .tracks(new ArrayList<>())
                    .artists(new ArrayList<>())
                    .images(new ArrayList<>())
                    .statistics(statistics)
                    .build()
            );
        }
        TestContainer testContainer = TestContainer.builder().build();
        albums = testContainer.albumRepository.saveAll(albums);

        Long cursorId = 1L;
        String cursorReviewCount = "9";
        int limit = 5;

        // when
        CursorPageResponse<AlbumListResponse> response = testContainer.albumService.getAlbumsByReviewCount(cursorId, cursorReviewCount, limit);

        // then
        assertAll(
                () -> assertThat(response.getLimit()).isEqualTo(5),
                () -> assertThat(response.isHasNext()).isEqualTo(false),
                () -> assertThat(response.getItems().size()).isEqualTo(4),
                () -> assertThat(response.getItems().get(0).getId()).isEqualTo("cursor-id9"),
                () -> assertThat(response.getItems().get(3).getId()).isEqualTo("cursor-id5")
        );
    }

}
