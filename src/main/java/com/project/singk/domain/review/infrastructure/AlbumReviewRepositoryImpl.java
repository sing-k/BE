package com.project.singk.domain.review.infrastructure;

import com.project.singk.domain.review.domain.AlbumReview;
import com.project.singk.domain.review.service.port.AlbumReviewRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AlbumReviewRepositoryImpl implements AlbumReviewRepository {
    private final AlbumReviewJpaRepository albumReviewJpaRepository;

    @Override
    public AlbumReview save(AlbumReview albumReview) {
        return albumReviewJpaRepository.save(AlbumReviewEntity.from(albumReview)).toModel();
    }

    @Override
    public AlbumReview getById(Long id) {
        return findById(id).orElseThrow(() -> new ApiException(AppHttpStatus.NOT_FOUND_ALBUM_REVIEW));
    }

    @Override
    public Optional<AlbumReview> findById(Long id) {
        return albumReviewJpaRepository.findById(id).map(AlbumReviewEntity::toModel);
    }
}
