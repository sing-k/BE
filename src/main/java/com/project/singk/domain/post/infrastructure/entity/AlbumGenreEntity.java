package com.project.singk.domain.post.infrastructure.entity;

import com.project.singk.domain.post.domain.AlbumGenre;
import com.project.singk.domain.post.domain.GenreType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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
