package com.project.singk.mock;

import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.album.service.port.AlbumRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;

import java.util.*;

public class FakeAlbumRepository implements AlbumRepository {
	private final List<Album> data = Collections.synchronizedList(new ArrayList<>());
	@Override
	public Album save(Album album) {
        data.removeIf(item -> Objects.equals(item.getId(), album.getId()));
        data.add(album);
        return album;
	}

    @Override
    public Album getById(String id) {
        return findById(id).orElseThrow(() -> new ApiException(AppHttpStatus.NOT_FOUND_ALBUM));
    }

    @Override
    public Optional<Album> findById(String id) {
        return data.stream().filter(item -> item.getId().equals(id)).findAny();
    }
}
