package com.project.singk.domain.post.infrastructure.util;

import com.project.singk.domain.post.domain.AlbumGenre;
import com.project.singk.domain.post.service.port.AlbumGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class GenreDataLoader implements CommandLineRunner {

    @Autowired
    private AlbumGenreRepository albumGenreRepository;

    @Override
    public void run(String... args) throws Exception {
        for (AlbumGenre genre : AlbumGenre.values()) {
            if (!albumGenreRepository.existsByGenre(genre))
                albumGenreRepository.save(genre);
        }
    }
}
