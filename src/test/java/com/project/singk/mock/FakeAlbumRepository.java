package com.project.singk.mock;

import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.album.service.port.AlbumRepository;
import com.project.singk.domain.review.domain.AlbumReviewStatistics;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public List<Album> saveAll(List<Album> albums) {
        return albums.stream().map(this::save).toList();
    }

    @Override
    public boolean existsById(String albumId) {
        return data.stream()
                .anyMatch(item -> item.getId().equals(albumId));
    }

    @Override
    public Album getById(String id) {
        return findById(id).orElseThrow(() -> new ApiException(AppHttpStatus.NOT_FOUND_ALBUM));
    }

    @Override
    public Album getByIdWithStatistics(String albumId) {
        return findByIdWithStatistics(albumId)
                .orElseThrow(() -> new ApiException(AppHttpStatus.NOT_FOUND_ALBUM));
    }

    @Override
    public AlbumReviewStatistics getAlbumReviewStatisticsByAlbumId(String albumId) {
        return data.stream()
                .filter(item -> item.getId().equals(albumId))
                .findAny()
                .orElseThrow(() -> new ApiException(AppHttpStatus.NOT_FOUND_ALBUM)).getStatistics();
    }

    @Override
    public Optional<Album> findByIdWithStatistics(String albumId) {
        return data.stream()
                .filter(item -> item.getId().equals(albumId))
                .findAny();
    }

    @Override
    public Optional<Album> findById(String id) {
        return data.stream().filter(item -> item.getId().equals(id)).findAny();
    }

    @Override
    public Page<Album> findAllByModifiedAt(String cursorId, String cursorDate, int limit) {


        List<Album> albums = data.stream()
                .filter(item -> cursorByDate(cursorId, cursorDate, item))
                .sorted((a, b) -> b.getStatistics().getModifiedAt().compareTo(a.getStatistics().getModifiedAt()))
                .limit(limit)
                .toList();

        Pageable pageable = PageRequest.ofSize(limit);

        return new PageImpl<>(albums, pageable, data.size());
    }

    private boolean cursorByDate(String cursorId, String cursorDate, Album item) {
        if (cursorId == null && cursorDate == null) {
            return true;
        }

        LocalDateTime date = LocalDateTime.parse(
                cursorDate,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        );

        return (item.getStatistics().getModifiedAt().equals(date) && !item.getId().equals(cursorId))
                || item.getStatistics().getModifiedAt().isBefore(date);
    }

    @Override
    public Page<Album> findAllByAverageScore(String cursorId, String cursorScore, int limit) {

        List<Album> albums = data.stream()
                .filter(item -> cursorByAverage(cursorId, cursorScore, item))
                .sorted((a, b) -> {
                    if(b.getStatistics().getAverageScore() > a.getStatistics().getAverageScore()) {
                        return 1;
                    } else if (b.getStatistics().getAverageScore() == a.getStatistics().getAverageScore()) {
                        return 0;
                    } else {
                        return  -1;
                    }
                })
                .limit(limit)
                .toList();

        Pageable pageable = PageRequest.ofSize(limit);

        return new PageImpl<>(albums, pageable, data.size());
    }

    private boolean cursorByAverage(String cursorId, String cursorScore, Album item) {
        if (cursorId == null && cursorScore == null) {
            return true;
        }

        double score = Double.parseDouble(cursorScore);

        return (item.getStatistics().getAverageScore() == score && !item.getId().equals(cursorId))
                || item.getStatistics().getAverageScore() < score;
    }
    @Override
    public Page<Album> findAllByReviewCount(String cursorId, String cursorReviewCount, int limit) {
        List<Album> albums = data.stream()
                .filter(item -> cursorByReviewCount(cursorId, cursorReviewCount, item))
                .sorted((a, b) -> b.getStatistics().getTotalReviewer() - a.getStatistics().getTotalReviewer())
                .limit(limit)
                .toList();

        Pageable pageable = PageRequest.ofSize(limit);

        return new PageImpl<>(albums, pageable, data.size());
    }

    private boolean cursorByReviewCount(String cursorId, String cursorReviewCount, Album item) {
        if (cursorId == null && cursorReviewCount == null) {
            return true;
        }

        long count = Long.parseLong(cursorReviewCount);

        return (item.getStatistics().getTotalReviewer() == count && !item.getId().equals(cursorId))
                || item.getStatistics().getTotalReviewer() < count;
    }
}
