package com.project.singk.domain.post.infrastructure.entity;

import com.project.singk.domain.post.domain.Genre;
import jakarta.persistence.*;
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
    @Column(name="name")
    private Genre genre;
}
