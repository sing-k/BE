package com.project.singk.domain.review.controller.port;

import com.project.singk.domain.review.domain.AlbumReviewCreate;
import com.project.singk.global.domain.PkResponseDto;

public interface ReviewService {
    PkResponseDto createAlbumReview(Long memberId, String albumId, AlbumReviewCreate albumReviewCreate);
}
