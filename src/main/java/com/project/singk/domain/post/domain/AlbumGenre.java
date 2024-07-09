package com.project.singk.domain.post.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AlbumGenre {
    @Builder
    public AlbumGenre(Long id, GenreType genre) {
        this.id = id;
        this.genre = genre;
    }

    private final Long id;
    private final GenreType genre;
}
