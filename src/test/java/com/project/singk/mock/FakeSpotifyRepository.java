package com.project.singk.mock;

import com.project.singk.domain.album.infrastructure.spotify.*;
import com.project.singk.domain.album.service.port.SpotifyRepository;

import com.project.singk.global.api.OffsetPageResponse;

import java.time.LocalDateTime;
import java.util.List;

public class FakeSpotifyRepository implements SpotifyRepository {
    @Override
    public AlbumEntity getAlbumById(String id) {
        List<ArtistSimplifiedEntity> artists = List.of(
                ArtistSimplifiedEntity.builder()
                        .id("6HvZYsbFfjnjFrWF950C9d")
                        .name("NewJeans")
                        .build()
        );
        List<TrackSimplifiedEntity> tracks = List.of(
                TrackSimplifiedEntity.builder()
                        .name("Bubble Gum")
                        .artists(artists)
                        .build(),
                TrackSimplifiedEntity.builder()
                        .name("How Sweet")
                        .artists(artists)
                        .build(),
                TrackSimplifiedEntity.builder()
                        .name("How Sweet (Instrumental)")
                        .artists(artists)
                        .build(),
                TrackSimplifiedEntity.builder()
                        .name("Bubble Gum (Instrumental)")
                        .artists(artists)
                        .build()
        );
        List<ImageEntity> images = List.of(
                ImageEntity.builder()
                        .imageUrl("https://i.scdn.co/image/ab67616d0000b273b657fbb27b17e7bd4691c2b2")
                        .width(640)
                        .height(640)
                        .build()
        );

        return AlbumEntity.builder()
                .id("0EhZEM4RRz0yioTgucDhJq")
                .name("How Sweet")
                .type("single")
                .releasedAt(LocalDateTime.of(2024, 5, 24, 0, 0, 0))
                .artists(artists)
                .tracks(tracks)
                .images(images)
                .build();
    }

    @Override
    public OffsetPageResponse<AlbumSimplifiedEntity> searchAlbums(String query, int offset, int limit) {
        List<ArtistSimplifiedEntity> artists = List.of(
                ArtistSimplifiedEntity.builder()
                        .id("6HvZYsbFfjnjFrWF950C9d")
                        .name("NewJeans")
                        .build()
        );
        List<ImageEntity> images = List.of(
                ImageEntity.builder()
                        .imageUrl("https://i.scdn.co/image/ab67616d0000b273b657fbb27b17e7bd4691c2b2")
                        .width(640)
                        .height(640)
                        .build()
        );
        List<AlbumSimplifiedEntity> albums = List.of(
                AlbumSimplifiedEntity.builder()
                        .id("0EhZEM4RRz0yioTgucDhJq")
                        .name("How Sweet")
                        .releasedAt(LocalDateTime.of(2024,5,24,0,0, 0))
                        .artists(artists)
                        .images(images)
                        .build()
        );
        return OffsetPageResponse.of(
                offset,
                limit,
                albums.size(),
                albums
        );
    }
}
