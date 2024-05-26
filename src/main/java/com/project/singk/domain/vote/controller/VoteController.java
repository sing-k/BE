package com.project.singk.domain.vote.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.singk.domain.vote.dto.VoteRequestDto;
import com.project.singk.domain.vote.service.VoteService;
import com.project.singk.global.api.BaseResponse;
import com.project.singk.global.domain.PkResponseDto;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/votes")
public class VoteController {

	private final VoteService voteService;

	@PostMapping("/albums/reviews/{albumReviewId}")
	public BaseResponse<PkResponseDto> createAlbumReviewVote(
		@PathVariable(name = "albumReviewId") Long albumReviewId,
		@RequestBody VoteRequestDto request
	) {
		return BaseResponse.ok(voteService.createAlbumReviewVote(albumReviewId, request));
	}

	@DeleteMapping("/albums/reviews/{albumReviewId}")
	public BaseResponse<Void> deleteAlbumReviewVote(
		@PathVariable(name = "albumReviewId") Long albumReviewId,
		@RequestBody VoteRequestDto request
	) {
		voteService.deleteAlbumReviewVote(albumReviewId, request);
		return BaseResponse.ok();
	}
}