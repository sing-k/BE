package com.project.singk.domain.album.domain;

import com.project.singk.domain.album.infrastructure.spotify.AlbumEntity;
import com.project.singk.domain.album.infrastructure.spotify.ArtistSimplifiedEntity;
import com.project.singk.domain.album.infrastructure.spotify.ImageEntity;
import com.project.singk.domain.album.infrastructure.spotify.TrackSimplifiedEntity;
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
    public void Album은_평점을_받아_총_평점을_증가시킬_수_있다() {
        // given
        Album album = Album.builder()
                .totalScore(0)
                .totalReviewer(0)
                .build();
        // when
        album = album.increaseReviewScore(4);

        // then
        assertThat(album.getTotalReviewer()).isEqualTo(1);
        assertThat(album.getTotalScore()).isEqualTo(4);
    }

    @Test
    public void Album은_평점을_받아_총_평점을_감소시킬_수_있다() {
        // given
        Album album = Album.builder()
                .totalScore(4)
                .totalReviewer(1)
                .build();
        // when
        album = album.decreaseReviewScore(4);

        // then
        assertThat(album.getTotalReviewer()).isEqualTo(0);
        assertThat(album.getTotalScore()).isEqualTo(0);
    }

    @Test
    public void Album은_평균_평점을_계산할_수_있다() {
        // given
        Album album = Album.builder()
                .totalScore(123)
                .totalReviewer(25)
                .build();
        // when
        double result = album.calculateAverage();

        // then
        assertThat(result).isEqualTo(4.92);
    }
}
