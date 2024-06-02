package com.project.singk.domain.album.service.port;

import com.project.singk.domain.album.domain.Track;

import java.util.List;

public interface TrackRepository {
    Track save(Track track);
    List<Track> saveAll(List<Track> tracks);
    List<Track> findByAlbumId(String albumId);
}
