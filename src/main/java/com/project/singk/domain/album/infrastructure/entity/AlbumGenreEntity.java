package com.project.singk.domain.album.infrastructure.entity;

import com.project.singk.domain.album.domain.AlbumGenre;
import com.project.singk.domain.album.domain.GenreType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Album_Genres")
@Getter
@NoArgsConstructor
public class AlbumGenreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private GenreType genre;

    @Builder
    public AlbumGenreEntity(Long id, GenreType genre) {
        this.id = id;
        this.genre = genre;
    }

    public static AlbumGenreEntity from(AlbumGenre genre) {
        return AlbumGenreEntity.builder()
                .id(genre.getId())
                .genre(genre.getGenre())
                .build();
    }

    public AlbumGenre toModel() {
        return AlbumGenre.builder()
                .id(this.id)
                .genre(this.genre)
                .build();
    }
}
