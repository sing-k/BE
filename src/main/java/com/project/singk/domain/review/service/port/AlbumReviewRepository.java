package com.project.singk.domain.review.service.port;

import com.project.singk.domain.review.domain.AlbumReview;

import java.util.Optional;

public interface AlbumReviewRepository {
    AlbumReview save(AlbumReview albumReview);
    AlbumReview getById(Long id);
    Optional<AlbumReview> findById(Long id);
}
