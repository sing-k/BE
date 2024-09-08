package com.project.singk.domain.album.domain;


import lombok.Builder;
import lombok.Getter;

@Getter
public class AlbumGenre {
    private final Long id;
    private final GenreType genre;

    @Builder
    public AlbumGenre(Long id, GenreType genre) {
        this.id = id;
        this.genre = genre;
    }
}
