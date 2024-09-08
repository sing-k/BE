package com.project.singk.mock;

import com.project.singk.domain.album.domain.Track;
import com.project.singk.domain.album.service.port.TrackRepository;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;

import java.util.*;

public class FakeTrackRepository implements TrackRepository {
	private final List<Track> data = Collections.synchronizedList(new ArrayList<>());

    @Override
    public Track save(Track track) {
        data.removeIf(item -> Objects.equals(item.getId(), track.getId()));
        data.add(track);
        return track;
    }

    @Override
    public List<Track> saveAll(List<Track> tracks) {
        return tracks.stream().map(this::save).toList();
    }

}
