package com.project.singk.domain.review.controller;

import com.project.singk.domain.member.controller.port.AuthService;
import com.project.singk.domain.review.controller.port.ReviewService;
import com.project.singk.domain.review.controller.request.ReviewSort;
import com.project.singk.domain.review.controller.response.AlbumReviewResponse;
import com.project.singk.domain.review.controller.response.AlbumReviewStatisticsResponse;
import com.project.singk.global.validate.ValidEnum;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.project.singk.domain.review.domain.AlbumReviewCreate;
import com.project.singk.global.api.BaseResponse;
import com.project.singk.global.domain.PkResponseDto;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final AuthService authService;
	private final ReviewService reviewService;

	@PostMapping("/albums/{albumId}")
	public BaseResponse<PkResponseDto> createAlbumReview(
		@PathVariable(name = "albumId") String albumId,
		@Valid @RequestBody AlbumReviewCreate request
	) {
		return BaseResponse.created(reviewService.createAlbumReview(
                authService.getLoginMemberId(),
                albumId,
                request
        ));
	}
    @GetMapping("/albums/{albumId}")
	public BaseResponse<List<AlbumReviewResponse>> getAlbumReviews(
		@PathVariable(name = "albumId") String albumId,
        @RequestParam(name = "sort") @ValidEnum(enumClass = ReviewSort.class) String sort
	) {
		return BaseResponse.ok(reviewService.getAlbumReviews(albumId, sort));
	}

    @DeleteMapping("{albumReviewId}/albums/{albumId}")
	public BaseResponse<Void> deleteAlbumReview(
        @PathVariable(name = "albumReviewId") Long albumReviewId,
		@PathVariable(name = "albumId") String albumId
	) {
        reviewService.deleteAlbumReview(
                authService.getLoginMemberId(),
                albumId,
                albumReviewId
        );
		return BaseResponse.ok();
	}

    @GetMapping("/albums/{albumId}/statistics")
    public BaseResponse<AlbumReviewStatisticsResponse> getAlbumReviewStatistics(
            @PathVariable(name = "albumId") String albumId
    ) {
        return BaseResponse.ok(reviewService.getAlbumReviewStatistics(
                albumId
        ));
    }
}
