package com.project.singk.mock;

import com.project.singk.domain.album.domain.AlbumImage;
import com.project.singk.domain.album.service.port.AlbumImageRepository;

import java.util.*;

public class FakeAlbumImageRepository implements AlbumImageRepository {
	private final List<AlbumImage> data = Collections.synchronizedList(new ArrayList<>());

    @Override
    public AlbumImage save(AlbumImage albumImage) {
        data.removeIf(item -> Objects.equals(item.getId(), albumImage.getId()));
        data.add(albumImage);
        return albumImage;
    }

    @Override
    public List<AlbumImage> saveAll(List<AlbumImage> albumImages) {
        return albumImages.stream().map(this::save).toList();
    }

    @Override
    public List<AlbumImage> findAllByAlbumId(String albumId) {
        return null;
    }
}
