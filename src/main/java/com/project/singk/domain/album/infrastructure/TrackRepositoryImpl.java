package com.project.singk.domain.album.infrastructure;

import com.project.singk.domain.album.domain.Track;
import com.project.singk.domain.album.infrastructure.entity.TrackEntity;
import com.project.singk.domain.album.infrastructure.jpa.TrackJpaRepository;
import com.project.singk.domain.album.service.port.TrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TrackRepositoryImpl implements TrackRepository {

    private final TrackJpaRepository trackJpaRepository;
    @Override
    public Track save(Track track) {
        return trackJpaRepository.save(TrackEntity.from(track)).toModel();
    }

    @Override
    public List<Track> saveAll(List<Track> tracks) {
        return trackJpaRepository.saveAll(tracks.stream()
                        .map(TrackEntity::from)
                        .toList()).stream()
                .map(TrackEntity::toModel)
                .toList();
    }

    @Override
    public List<Track> findByAlbumId(String albumId) {
        return trackJpaRepository.findByAlbumId(albumId).stream()
                .map(TrackEntity::toModel)
                .toList();
    }
}
