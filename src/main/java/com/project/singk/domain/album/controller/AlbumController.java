package com.project.singk.domain.album.controller;

import com.project.singk.domain.album.controller.request.AlbumSort;
import com.project.singk.global.validate.Date;
import com.project.singk.global.validate.ValidDecimal;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.singk.domain.album.controller.port.AlbumService;
import com.project.singk.domain.album.controller.response.AlbumDetailResponse;
import com.project.singk.domain.album.controller.response.AlbumListResponse;
import com.project.singk.global.api.BaseResponse;
import com.project.singk.global.api.PageResponse;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/albums")
public class AlbumController {

	private final AlbumService albumService;

	@GetMapping("/{albumId}")
	public BaseResponse<AlbumDetailResponse> getAlbum(
		@PathVariable("albumId") String albumId
	) {
		return BaseResponse.ok(albumService.getAlbum(albumId));
	}

	@GetMapping("/search")
	public BaseResponse<PageResponse<AlbumListResponse>> searchAlbums(
		@RequestParam(value = "query", required = false) String query,
		@Range(min = 0, max = 1000, message = "offset은 0에서 1000사이의 값 이어야 합니다.") @RequestParam("offset") int offset,
		@Range(min = 0, max = 50, message = "limit은 0에서 50사이의 값 이어야 합니다.") @RequestParam("limit") int limit
	) {
		return BaseResponse.ok(albumService.searchAlbums(query, offset, limit));
	}

    @GetMapping("/list/recent")
    public BaseResponse<PageResponse<AlbumListResponse>> getAlbumsByDate(
            @RequestParam(value = "cursor-id", required = false) String cursorId,
            @Date @RequestParam(value = "cursor-date", required = false) String cursorDate,
            @Range(min = 0, max = 50, message = "limit은 0에서 50사이의 값 이어야 합니다.") @RequestParam("limit") int limit
    ) {
        return BaseResponse.ok(albumService.getAlbumsByDate(cursorId, cursorDate, limit));
    }

    @GetMapping("/list/average-score")
    public BaseResponse<PageResponse<AlbumListResponse>> getAlbumsByAverage(
            @RequestParam(value = "cursor-id", required = false) String cursorId,
            @ValidDecimal @RequestParam(value = "cursor-score", required = false) String cursorScore,
            @Range(min = 0, max = 50, message = "limit은 0에서 50사이의 값 이어야 합니다.") @RequestParam("limit") int limit
    ) {

        return BaseResponse.ok(albumService.getAlbumsByAverageScore(cursorId, cursorScore, limit));
    }
    @GetMapping("/list/review-count")
    public BaseResponse<PageResponse<AlbumListResponse>> getAlbumsByReviewCount(
            @RequestParam(value = "cursor-id", required = false) String cursorId,
            @RequestParam(value = "cursor-review-count", required = false) String cursorReviewCount,
            @Range(min = 0, max = 50, message = "limit은 0에서 50사이의 값 이어야 합니다.") @RequestParam("limit") int limit
    ) {

        return BaseResponse.ok(albumService.getAlbumsByReviewCount(cursorId, cursorReviewCount, limit));
    }
}
