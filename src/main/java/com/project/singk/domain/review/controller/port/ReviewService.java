package com.project.singk.domain.review.controller.port;

import com.project.singk.domain.review.controller.response.AlbumReviewResponse;
import com.project.singk.domain.review.controller.response.AlbumReviewStatisticsResponse;
import com.project.singk.domain.review.controller.response.MyAlbumReviewResponse;
import com.project.singk.domain.review.domain.AlbumReviewCreate;
import com.project.singk.global.api.OffsetPageResponse;
import com.project.singk.global.domain.PkResponseDto;

public interface ReviewService {
    PkResponseDto createAlbumReview(Long memberId, String albumId, AlbumReviewCreate albumReviewCreate);
    void deleteAlbumReview(Long memberId, String albumId, Long albumReviewId);
    OffsetPageResponse<AlbumReviewResponse> getAlbumReviews(String albumId, int offset, int limit, String sort);
    AlbumReviewStatisticsResponse getAlbumReviewStatistics(String albumId);
    OffsetPageResponse<MyAlbumReviewResponse> getMyAlbumReview(Long memberId, int offset, int limit, String sort);
}
