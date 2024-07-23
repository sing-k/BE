package com.project.singk.domain.post.infrastructure.util;

import com.project.singk.domain.post.domain.AlbumGenre;
import com.project.singk.domain.post.domain.GenreType;
import com.project.singk.domain.post.service.port.AlbumGenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GenreDataLoader implements CommandLineRunner {

    private final AlbumGenreRepository albumGenreRepository;

    @Override
    public void run(String... args) throws Exception {
        long i=1L;
        for (GenreType genre : GenreType.values()) {
            if (!albumGenreRepository.existsByGenre(genre))
                albumGenreRepository.save(AlbumGenre.builder().
                        id(i++).
                        genre(genre).
                        build());
        }
    }
}
