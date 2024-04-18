package com.project.singk.domain.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.singk.domain.album.service.AlbumService;
import com.project.singk.global.api.BaseResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
	private final AlbumService albumService;

	@GetMapping("/crawling/melon")
	public BaseResponse<Void> crawlingMelonAlbums(@RequestParam("startId") Long startId, @RequestParam("endId") Long endId) {
		albumService.crawlingMelonAlbums(startId, endId);
		return BaseResponse.ok();
	}


}
