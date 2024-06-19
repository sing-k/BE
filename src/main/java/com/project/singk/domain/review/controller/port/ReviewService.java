package com.project.singk.domain.review.controller.port;

import com.project.singk.domain.review.controller.response.AlbumReviewResponse;
import com.project.singk.domain.review.controller.response.AlbumReviewStatisticsResponse;
import com.project.singk.domain.review.controller.response.MyAlbumReviewResponse;
import com.project.singk.domain.review.domain.AlbumReviewCreate;
import com.project.singk.global.api.PageResponse;
import com.project.singk.global.domain.PkResponseDto;

import java.util.List;

public interface ReviewService {
    PkResponseDto createAlbumReview(Long memberId, String albumId, AlbumReviewCreate albumReviewCreate);
    void deleteAlbumReview(Long memberId, String albumId, Long albumReviewId);
    PageResponse<AlbumReviewResponse> getAlbumReviews(String albumId, int offset, int limit, String sort);
    AlbumReviewStatisticsResponse getAlbumReviewStatistics(String albumId);
    PageResponse<MyAlbumReviewResponse> getMyAlbumReview(Long memberId, int offset, int limit, String sort);
}
