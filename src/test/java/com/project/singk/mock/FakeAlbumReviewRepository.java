package com.project.singk.mock;

import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.member.domain.Gender;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.review.controller.request.ReviewSort;
import com.project.singk.domain.review.domain.AlbumReview;
import com.project.singk.domain.review.domain.AlbumReviewStatistics;
import com.project.singk.domain.review.service.port.AlbumReviewRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class FakeAlbumReviewRepository implements AlbumReviewRepository {
	private final AtomicLong autoGeneratedId = new AtomicLong(0);
	private final List<AlbumReview> data = Collections.synchronizedList(new ArrayList<>());

    @Override
    public AlbumReview save(AlbumReview albumReview) {
        if (albumReview.getId() == null || albumReview.getId() == 0) {
            AlbumReview newAlbumReview = AlbumReview.builder()
                    .id(autoGeneratedId.incrementAndGet())
                    .content(albumReview.getContent())
                    .score(albumReview.getScore())
                    .prosCount(albumReview.getProsCount())
                    .consCount(albumReview.getConsCount())
                    .album(albumReview.getAlbum())
                    .reviewer(albumReview.getReviewer())
                    .build();
            data.add(newAlbumReview);
            return newAlbumReview;
        } else {
            data.removeIf(item -> Objects.equals(item.getId(), albumReview.getId()));
            data.add(albumReview);
            return albumReview;
        }
    }

    @Override
    public List<AlbumReview> saveAll(List<AlbumReview> albumReviews) {
        return albumReviews.stream().map(this::save).toList();
    }

    @Override
    public AlbumReview getById(Long id) {
        return findById(id).orElseThrow(() -> new ApiException(AppHttpStatus.NOT_FOUND_ALBUM_REVIEW));
    }

    @Override
    public Optional<AlbumReview> findById(Long id) {
        return data.stream().filter(item -> item.getId().equals(id)).findAny();
    }

    @Override
    public boolean existsByMemberAndAlbum(Member member, Album album) {
        return data.stream().anyMatch(item ->
                item.getReviewer().getId().equals(member.getId()) &&
                item.getAlbum().getId().equals(album.getId()));
    }

    @Override
    public List<AlbumReview> getAllByAlbumId(String albumId, ReviewSort sort) {

        switch (sort) {
            case NEW -> {
                return data.stream()
                        .filter(item -> item.getAlbum().getId().equals(albumId))
                        .sorted(Comparator.comparing(AlbumReview::getCreatedAt).reversed())
                        .toList();
            }
            case LIKES -> {
                return data.stream()
                        .filter(item -> item.getAlbum().getId().equals(albumId))
                        .sorted(Comparator.comparing(AlbumReview::getProsCount).reversed())
                        .toList();
            }
        }

        return data.stream()
                .filter(item -> item.getAlbum().getId().equals(albumId))
                .sorted(Comparator.comparing(AlbumReview::getCreatedAt).reversed())
                .toList();
    }

    @Override
    public AlbumReviewStatistics getAlbumReviewStatisticsByAlbumId(String albumId) {
        List<AlbumReview> albumReviews = data.stream().filter(item -> item.getAlbum().getId().equals(albumId)).toList();

        int totalReviewer = albumReviews.size();
        int totalScore = albumReviews.stream().map(AlbumReview::getScore).reduce(0, Integer::sum);

        return AlbumReviewStatistics.builder()
                .totalReviewer(totalReviewer)
                .totalScore(totalScore)
                .score1Count((int) albumReviews.stream()
                        .filter(item -> item.getScore() == 1)
                        .count())
                .score2Count((int) albumReviews.stream()
                        .filter(item -> item.getScore() == 2)
                        .count())
                .score3Count((int) albumReviews.stream()
                        .filter(item -> item.getScore() == 3)
                        .count())
                .score4Count((int) albumReviews.stream()
                        .filter(item -> item.getScore() == 4)
                        .count())
                .score5Count((int) albumReviews.stream()
                        .filter(item -> item.getScore() == 5)
                        .count())
                .maleCount((int) albumReviews.stream()
                        .filter(item -> item.getReviewer().getGender() == Gender.MALE)
                        .count())
                .maleTotalScore(albumReviews.stream()
                        .filter(item -> item.getReviewer().getGender() == Gender.MALE)
                        .map(AlbumReview::getScore)
                        .reduce(0, Integer::sum))
                .femaleCount((int) albumReviews.stream()
                        .filter(item -> item.getReviewer().getGender() == Gender.FEMALE)
                        .count())
                .femaleTotalScore(albumReviews.stream()
                        .filter(item -> item.getReviewer().getGender() == Gender.FEMALE)
                        .map(AlbumReview::getScore)
                        .reduce(0, Integer::sum))
                .build();
    }

    @Override
    public void delete(AlbumReview albumReview) {
        data.removeIf(item -> item.getId().equals(albumReview.getId()));
    }

}
