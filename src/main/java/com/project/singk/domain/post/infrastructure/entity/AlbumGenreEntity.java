package com.project.singk.domain.post.infrastructure.entity;

import com.project.singk.domain.post.domain.AlbumGenre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Album_Genres")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AlbumGenreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private AlbumGenre genre;

    public static AlbumGenreEntity from(AlbumGenre genre) {
        return new AlbumGenreEntity((long) genre.ordinal(), genre);
    }

    public AlbumGenre toModel() {
        return this.genre;
    }
}
