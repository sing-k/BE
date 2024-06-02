package com.project.singk.domain.album.service.port;

import com.project.singk.domain.album.domain.AlbumImage;

import java.util.List;

public interface AlbumImageRepository {
    AlbumImage save(AlbumImage albumImage);
    List<AlbumImage> saveAll(List<AlbumImage> albumImages);
    List<AlbumImage> findByAlbumId(String albumId);
}
