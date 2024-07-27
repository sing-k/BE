package com.project.singk.domain.album.infrastructure;

import com.project.singk.domain.album.domain.AlbumImage;
import com.project.singk.domain.album.infrastructure.entity.AlbumImageEntity;
import com.project.singk.domain.album.infrastructure.jpa.AlbumImageJpaRepository;
import com.project.singk.domain.album.service.port.AlbumImageRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.project.singk.domain.album.infrastructure.entity.QAlbumEntity.albumEntity;

@Repository
@RequiredArgsConstructor
public class AlbumImageRepositoryImpl implements AlbumImageRepository {

    private final AlbumImageJpaRepository albumImageJpaRepository;
    private final JPAQueryFactory queryFactory;
    @Override
    public AlbumImage save(AlbumImage albumImage) {
        return albumImageJpaRepository.save(AlbumImageEntity.from(albumImage)).toModel();
    }

    @Override
    public List<AlbumImage> saveAll(List<AlbumImage> albumImages) {
        return albumImageJpaRepository.saveAll(albumImages.stream()
                        .map(AlbumImageEntity::from)
                        .toList()).stream()
                .map(AlbumImageEntity::toModel)
                .toList();
    }

    @Override
    public List<AlbumImage> findAllByAlbumId(String albumId) {
        return queryFactory.select(albumEntity.images)
                .from(albumEntity)
                .where(albumEntity.id.eq(albumId))
                .fetchOne().stream()
                .map(AlbumImageEntity::toModel)
                .toList();
    }
}
