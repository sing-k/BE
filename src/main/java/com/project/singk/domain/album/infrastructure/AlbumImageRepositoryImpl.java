package com.project.singk.domain.album.infrastructure;

import com.project.singk.domain.album.domain.AlbumImage;
import com.project.singk.domain.album.domain.Track;
import com.project.singk.domain.album.infrastructure.entity.AlbumImageEntity;
import com.project.singk.domain.album.infrastructure.entity.TrackEntity;
import com.project.singk.domain.album.infrastructure.jpa.AlbumImageJpaRepository;
import com.project.singk.domain.album.infrastructure.jpa.TrackJpaRepository;
import com.project.singk.domain.album.service.port.AlbumImageRepository;
import com.project.singk.domain.album.service.port.TrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AlbumImageRepositoryImpl implements AlbumImageRepository {

    private final AlbumImageJpaRepository albumImageJpaRepositoryJpaRepository;
    @Override
    public AlbumImage save(AlbumImage albumImage) {
        return albumImageJpaRepositoryJpaRepository.save(AlbumImageEntity.from(albumImage)).toModel();
    }

    @Override
    public List<AlbumImage> saveAll(List<AlbumImage> albumImages) {
        return albumImageJpaRepositoryJpaRepository.saveAll(albumImages.stream()
                        .map(AlbumImageEntity::from)
                        .toList()).stream()
                .map(AlbumImageEntity::toModel)
                .toList();
    }

    @Override
    public List<AlbumImage> findByAlbumId(String albumId) {
        return albumImageJpaRepositoryJpaRepository.findByAlbumId(albumId).stream()
                .map(AlbumImageEntity::toModel)
                .toList();
    }
}
