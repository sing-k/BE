package com.project.singk.domain.album.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AlbumArtist {
    private final Long id;
    private final Artist artist;

    @Builder
    public AlbumArtist(Long id, Artist artist) {
        this.id = id;
        this.artist = artist;
    }

    public static AlbumArtist from (Artist artist) {
        return AlbumArtist.builder()
                .artist(artist)
                .build();
    }
}
