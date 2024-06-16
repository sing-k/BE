package com.project.singk.domain.album.domain;

import com.project.singk.domain.album.infrastructure.spotify.AlbumEntity;
import com.project.singk.domain.album.infrastructure.spotify.ArtistSimplifiedEntity;
import com.project.singk.domain.album.infrastructure.spotify.ImageEntity;
import com.project.singk.domain.album.infrastructure.spotify.TrackSimplifiedEntity;
import com.project.singk.domain.review.domain.AlbumReviewStatistics;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AlbumTest {

    @Test
    public void Spotify_AlbumEntity로_Album을_생성할_수_있다() {
        // given
        List<TrackSimplifiedEntity> trackSimplifiedEntities = List.of(
                TrackSimplifiedEntity.builder()
                        .name("Bubble Gum")
                        .build(),
                TrackSimplifiedEntity.builder()
                        .name("How Sweet")
                        .build(),
                TrackSimplifiedEntity.builder()
                        .name("How Sweet (Instrumental)")
                        .build(),
                TrackSimplifiedEntity.builder()
                        .name("Bubble Gum (Instrumental)")
                        .build()
        );

        List<ArtistSimplifiedEntity> artistSimplifiedEntities = List.of(
                ArtistSimplifiedEntity.builder()
                        .name("NewJeans")
                        .build()
        );

        List<ImageEntity> imageEntities = List.of(
                ImageEntity.builder()
                        .imageUrl("image")
                        .build()
        );

        AlbumEntity albumEntity = AlbumEntity.builder()
                .id("0EhZEM4RRz0yioTgucDhJq")
                .name("How Sweet")
                .type("single")
                .releasedAt(LocalDate.parse("2024-05-24").atStartOfDay())
                .tracks(trackSimplifiedEntities)
                .artists(artistSimplifiedEntities)
                .images(imageEntities)
                .build();

        // when
        Album album = albumEntity.toModel();

        // then
        assertAll(
                () -> assertThat(album.getId()).isEqualTo("0EhZEM4RRz0yioTgucDhJq"),
                () -> assertThat(album.getName()).isEqualTo("How Sweet"),
                () -> assertThat(album.getType()).isEqualTo(AlbumType.EP),
                () -> assertThat(album.getReleasedAt()).isEqualTo(LocalDateTime.of(2024,5,24, 0, 0, 0))
        );
    }

    @Test
    public void Album은_통계를_업데이트할_수_있다() {

        // given
        AlbumReviewStatistics statistics = AlbumReviewStatistics.builder()
                .totalScore(50)
                .totalReviewer(10)
                .averageScore(5.0)
                .modifiedAt(LocalDateTime.of(2024, 6,16, 0,0,0))
                .build();

        Album album = Album.builder()
                .id("0EhZEM4RRz0yioTgucDhJq")
                .name("How Sweet")
                .type(AlbumType.EP)
                .releasedAt(LocalDateTime.of(2024, 5, 24, 0, 0, 0))
                .build();

        // when
        album = album.updateStatistic(statistics);

        // then
        assertThat(album.getStatistics()).isNotNull();
        assertThat(album.getStatistics().getAverageScore()).isEqualTo(5.0);
        assertThat(album.getStatistics().getTotalScore()).isEqualTo(50);
        assertThat(album.getStatistics().getTotalReviewer()).isEqualTo(10);
    }


}
