package com.project.singk.domain.review.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.singk.domain.review.dto.AlbumReviewRequestDto;
import com.project.singk.domain.review.service.ReviewService;
import com.project.singk.global.api.BaseResponse;
import com.project.singk.global.domain.PkResponseDto;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

	private final ReviewService reviewService;

	@PostMapping("/albums/{albumId}")
	public BaseResponse<PkResponseDto> createAlbumReview(
		@PathVariable(name = "albumId") String albumId,
		@RequestBody AlbumReviewRequestDto request
	) {
		return BaseResponse.ok(reviewService.createAlbumReview(albumId, request));
	}
}
