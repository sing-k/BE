package com.project.singk.domain.album.controller.response;

import com.project.singk.domain.album.domain.*;
import com.project.singk.domain.review.domain.AlbumReviewStatistics;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AlbumDetailResponseTest {

    @Test
    public void Album으로_AlbumDetailResponse를_만들_수_있다() {
        // given

        List<Artist> artists = List.of(
                Artist.builder()
                        .name("NewJeans")
                        .build()
        );

        List<Track> tracks = List.of(
                Track.builder()
                        .name("Bubble Gum")
                        .artists(artists.stream()
                                .map(TrackArtist::from)
                                .toList())
                        .build(),
                Track.builder()
                        .name("How Sweet")
                        .artists(artists.stream()
                                .map(TrackArtist::from)
                                .toList())
                        .build(),
                Track.builder()
                        .name("How Sweet (Instrumental)")
                        .artists(artists.stream()
                                .map(TrackArtist::from)
                                .toList())
                        .build(),
                Track.builder()
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

        // when
        final AlbumDetailResponse response = AlbumDetailResponse.from(album);

        // then
        assertAll(
                () -> assertThat(response.getId()).isEqualTo("0EhZEM4RRz0yioTgucDhJq"),
                () -> assertThat(response.getName()).isEqualTo("How Sweet"),
                () -> assertThat(response.getType()).isEqualTo("EP"),
                () -> assertThat(response.getReleasedAt()).isEqualTo(LocalDateTime.of(2024, 5, 24, 0, 0, 0)),
                () -> assertThat(response.getTrackCount()).isEqualTo(4),
                () -> assertThat(response.getTracks().get(0).getName()).isEqualTo("Bubble Gum"),
                () -> assertThat(response.getTracks().get(0).getArtists().get(0).getName()).isEqualTo("NewJeans"),
                () -> assertThat(response.getAverageScore()).isEqualTo(0.0),
                () -> assertThat(response.getCount()).isEqualTo(0),
                () -> assertThat(response.getArtists().get(0).getName()).isEqualTo("NewJeans"),
                () -> assertThat(response.getImages().get(0).getImageUrl()).isEqualTo("https://i.scdn.co/image/ab67616d0000b273b657fbb27b17e7bd4691c2b2")
        );
    }
}
