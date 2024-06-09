package com.project.singk.domain.review.controller.port;

import com.project.singk.domain.review.controller.request.ReviewSort;
import com.project.singk.domain.review.controller.response.AlbumReviewResponse;
import com.project.singk.domain.review.controller.response.AlbumReviewStatisticsResponse;
import com.project.singk.domain.review.domain.AlbumReviewCreate;
import com.project.singk.global.domain.PkResponseDto;

import java.util.List;

public interface ReviewService {
    PkResponseDto createAlbumReview(Long memberId, String albumId, AlbumReviewCreate albumReviewCreate);
    void deleteAlbumReview(Long memberId, String albumId, Long albumReviewId);
    List<AlbumReviewResponse> getAlbumReviews(String albumId, String sort);
    AlbumReviewStatisticsResponse getAlbumReviewStatistics(String albumId);
}
