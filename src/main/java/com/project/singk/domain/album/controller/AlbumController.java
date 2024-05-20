package com.project.singk.domain.album.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.singk.domain.album.dto.AlbumResponseDto;
import com.project.singk.domain.album.service.AlbumService;
import com.project.singk.global.api.BaseResponse;
import com.project.singk.global.api.PageResponse;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/albums")
public class AlbumController {

	private final AlbumService albumService;

	@GetMapping("/search")
	public BaseResponse<PageResponse<AlbumResponseDto.Simple>> searchAlbums(
		@RequestParam(value = "query", required = false) String query,
		@Min(0) @Max(1000) @RequestParam("offset") int offset,
		@Min(0) @Max(50) @RequestParam("limit") int limit
	) {
		return BaseResponse.ok(albumService.searchAlbums(query, offset, limit));
	}

	@GetMapping("/{id}")
	public BaseResponse<AlbumResponseDto.Detail> getAlbum(
		@PathVariable("id") String id
	) {
		return BaseResponse.ok(albumService.getAlbums(id));
	}

}
