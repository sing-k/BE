package com.project.singk.domain.album.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.singk.domain.album.dto.AlbumListResponseDto;
import com.project.singk.domain.album.service.AlbumService;
import com.project.singk.global.api.BaseResponse;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/albums")
public class AlbumController {

	private final AlbumService albumService;

	@GetMapping("/random")
	public BaseResponse<List<AlbumListResponseDto>> getRandomAlbums(
		@RequestParam("limit")
		@Min(1) @Max(8) Long limit) {

		return BaseResponse.ok(albumService.getRandomAlbums(limit));
	}
}
