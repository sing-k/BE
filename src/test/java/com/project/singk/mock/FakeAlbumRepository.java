package com.project.singk.mock;

import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.album.domain.AlbumSimplified;
import com.project.singk.domain.album.service.port.AlbumRepository;
import com.project.singk.domain.review.domain.AlbumReviewStatistics;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
    public List<AlbumSimplified> findAll() {
        return data.stream()
                .map(this::simplified)
                .toList();
    }

    @Override
    public Page<AlbumSimplified> findAllWithOffsetPaging(int offset, int limit) {
        List<AlbumSimplified> albums = data.stream()
                .skip(offset)
                .limit(limit)
                .map(this::simplified)
                .toList();

        Pageable pageable = Pageable.ofSize(limit);

        return new PageImpl<>(albums, pageable, data.size());
    }

    @Override
    public List<AlbumSimplified> findAllWithCursorPaging(Long cursorId, String cursorDate, int limit) {
        return this.findAllByModifiedAt(cursorId, cursorDate, limit);
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
    public List<AlbumSimplified> findAllByModifiedAt(Long cursorId, String cursorDate, int limit) {
        return data.stream()
                .filter(item -> cursorByModifiedAt(cursorId, cursorDate, item))
                .sorted((a, b) -> b.getStatistics().getModifiedAt().compareTo(a.getStatistics().getModifiedAt()))
                .limit(limit)
                .map(this::simplified)
                .toList();
    }

    private boolean cursorByModifiedAt(Long cursorId, String cursorDate, Album item) {
        if (cursorId == null && cursorDate == null) {
            return true;
        }

        LocalDateTime date = LocalDateTime.parse(
                cursorDate,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        );

        return (item.getStatistics().getModifiedAt().equals(date) && !item.getStatistics().getId().equals(cursorId))
                || item.getStatistics().getModifiedAt().isBefore(date);
    }
    @Override
    public List<AlbumSimplified> findAllByAverageScore(Long cursorId, String cursorScore, int limit) {
        return data.stream()
                .filter(item -> cursorByAverage(cursorId, cursorScore, item))
                .sorted((a, b) -> Double.compare(b.getStatistics().getAverageScore(), a.getStatistics().getAverageScore()))
                .limit(limit)
                .map(this::simplified)
                .toList();
    }

    private boolean cursorByAverage(Long cursorId, String cursorScore, Album item) {
        if (cursorId == null && cursorScore == null) {
            return true;
        }

        double score = Double.parseDouble(cursorScore);

        return (item.getStatistics().getAverageScore() == score && !item.getStatistics().getId().equals(cursorId))
                || item.getStatistics().getAverageScore() < score;
    }
    @Override
    public List<AlbumSimplified> findAllByReviewCount(Long cursorId, String cursorReviewCount, int limit) {
        return data.stream()
                .filter(item -> cursorByReviewCount(cursorId, cursorReviewCount, item))
                .sorted((a, b) -> b.getStatistics().getTotalReviewer() - a.getStatistics().getTotalReviewer())
                .limit(limit)
                .map(this::simplified)
                .toList();
    }

    private boolean cursorByReviewCount(Long cursorId, String cursorReviewCount, Album item) {
        if (cursorId == null && cursorReviewCount == null) {
            return true;
        }

        long count = Long.parseLong(cursorReviewCount);

        return (item.getStatistics().getTotalReviewer() == count && !item.getStatistics().getId().equals(cursorId))
                || item.getStatistics().getTotalReviewer() < count;
    }

    private AlbumSimplified simplified(Album album) {
        return AlbumSimplified.builder()
                .id(album.getId())
                .name(album.getName())
                .type(album.getType())
                .releasedAt(album.getReleasedAt())
                .artists(album.getArtists())
                .images(album.getImages())
                .statistics(album.getStatistics())
                .createdAt(album.getCreatedAt())
                .build();
    }
}
