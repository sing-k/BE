package com.project.singk.domain.album.service.port;

import com.project.singk.domain.album.domain.Artist;

import java.util.List;

public interface ArtistRepository {
    Artist save(Artist artist);
    List<Artist> saveAll(List<Artist> artists);
}
