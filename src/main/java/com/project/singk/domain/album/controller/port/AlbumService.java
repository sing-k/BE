package com.project.singk.domain.album.controller.port;

import com.project.singk.domain.album.controller.request.AlbumSort;
import com.project.singk.domain.album.controller.response.AlbumDetailResponse;
import com.project.singk.domain.album.controller.response.AlbumListResponse;
import com.project.singk.global.api.PageResponse;

public interface AlbumService {
	AlbumDetailResponse getAlbum(String id);
	PageResponse<AlbumListResponse> searchAlbums(String query, int offset, int limit);
	PageResponse<AlbumListResponse> getAlbumsByDate(String cursorId, String cursorDate, int limit);
	PageResponse<AlbumListResponse> getAlbumsByAverageScore(String cursorId, String cursorScore, int limit);
	PageResponse<AlbumListResponse> getAlbumsByReviewCount(String cursorId, String cursorReviewCount, int limit);
}
