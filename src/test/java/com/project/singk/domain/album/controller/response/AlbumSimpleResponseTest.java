package com.project.singk.domain.album.controller.response;

import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.album.domain.AlbumImage;
import com.project.singk.domain.album.domain.AlbumType;
import com.project.singk.domain.album.domain.Artist;
import com.project.singk.domain.review.domain.AlbumReviewStatistics;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AlbumSimpleResponseTest {
    @Test
    public void Album으로_AlbumSimpleResponseTest를_만들_수_있다() {
        // given
        List<Artist> artists = List.of(
                Artist.builder()
                        .name("NewJeans")
                        .build()
        );
        List<AlbumImage> images = List.of(
                AlbumImage.builder()
                        .imageUrl("https://i.scdn.co/image/ab67616d0000b273b657fbb27b17e7bd4691c2b2")
                        .build()
        );

        AlbumReviewStatistics statistics = AlbumReviewStatistics.empty();

        Album album = Album.builder()
                .id("0EhZEM4RRz0yioTgucDhJq")
                .name("How Sweet")
                .type(AlbumType.EP)
                .releasedAt(LocalDateTime.of(2024, 5, 24, 0, 0, 0))
                .artists(artists)
                .images(images)
                .statistics(statistics)
                .build();

        // when
        AlbumSimpleResponse response = AlbumSimpleResponse.from(album);

        // then
        assertAll(
                () -> assertThat(response.getId()).isEqualTo("0EhZEM4RRz0yioTgucDhJq"),
                () -> assertThat(response.getName()).isEqualTo("How Sweet"),
                () -> assertThat(response.getArtists().get(0).getName()).isEqualTo("NewJeans"),
                () -> assertThat(response.getImages().get(0).getImageUrl()).isEqualTo("https://i.scdn.co/image/ab67616d0000b273b657fbb27b17e7bd4691c2b2")
        );
    }
}
