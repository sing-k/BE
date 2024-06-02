package com.project.singk.domain.review.controller;

import com.project.singk.domain.member.controller.port.AuthService;
import com.project.singk.domain.review.controller.port.ReviewService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.singk.domain.review.domain.AlbumReviewCreate;
import com.project.singk.global.api.BaseResponse;
import com.project.singk.global.domain.PkResponseDto;

import lombok.RequiredArgsConstructor;

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
		return BaseResponse.ok(reviewService.createAlbumReview(
                authService.getLoginMemberId(),
                albumId,
                request
        ));
	}
}
