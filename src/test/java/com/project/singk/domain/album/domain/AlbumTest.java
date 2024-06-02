package com.project.singk.domain.album.domain;

import com.project.singk.domain.album.infrastructure.spotify.AlbumEntity;
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

        AlbumEntity albumEntity = AlbumEntity.builder()
                .id("0EhZEM4RRz0yioTgucDhJq")
                .name("How Sweet")
                .type("single")
                .releasedAt(LocalDate.parse("2024-05-24").atStartOfDay())
                .tracks(trackSimplifiedEntities)
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
}
