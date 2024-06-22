package com.project.singk.mock;

import com.project.singk.domain.album.domain.Artist;
import com.project.singk.domain.album.service.port.ArtistRepository;

import java.util.*;

public class FakeArtistRepository implements ArtistRepository {
	private final List<Artist> data = Collections.synchronizedList(new ArrayList<>());

    @Override
    public Artist save(Artist artist) {
        data.removeIf(item -> Objects.equals(item.getId(), artist.getId()));
        data.add(artist);
        return artist;
    }

    @Override
    public List<Artist> saveAll(List<Artist> artists) {
        return artists.stream().map(this::save).toList();
    }

    @Override
    public boolean existById(String artistId) {
        return data.stream()
                .anyMatch(item -> item.getId().equals(artistId));
    }

}
