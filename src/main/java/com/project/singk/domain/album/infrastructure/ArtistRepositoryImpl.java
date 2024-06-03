package com.project.singk.domain.album.infrastructure;

import com.project.singk.domain.album.domain.Artist;
import com.project.singk.domain.album.infrastructure.entity.ArtistEntity;
import com.project.singk.domain.album.infrastructure.jpa.ArtistJpaRepository;
import com.project.singk.domain.album.service.port.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ArtistRepositoryImpl implements ArtistRepository {

    private final ArtistJpaRepository artistJpaRepository;
    @Override
    public Artist save(Artist artist) {
        return artistJpaRepository.save(ArtistEntity.from(artist)).toModel();
    }

    @Override
    public List<Artist> saveAll(List<Artist> artists) {
        return artistJpaRepository.saveAll(artists.stream()
                .map(ArtistEntity::from)
                .toList()).stream()
                .map(ArtistEntity::toModel)
                .toList();
    }

    @Override
    public List<Artist> findByAlbumId(String albumId) {
        return artistJpaRepository.findByAlbumId(albumId).stream()
                .map(ArtistEntity::toModel)
                .toList();
    }
}
