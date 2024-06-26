package com.project.singk.domain.album.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TrackArtist {
    private final Long id;
    private final Artist artist;

    @Builder
    public TrackArtist(Long id, Artist artist) {
        this.id = id;
        this.artist = artist;
    }

    public static TrackArtist from (Artist artist) {
        return TrackArtist.builder()
                .artist(artist)
                .build();
    }
}
