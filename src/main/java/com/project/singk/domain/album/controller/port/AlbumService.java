package com.project.singk.domain.album.controller.port;

import com.project.singk.domain.album.controller.response.AlbumDetailResponse;
import com.project.singk.domain.album.controller.response.AlbumListResponse;
import com.project.singk.global.api.CursorPageResponse;
import com.project.singk.global.api.OffsetPageResponse;

public interface AlbumService {
	AlbumDetailResponse getAlbum(String id);
	OffsetPageResponse<AlbumListResponse> searchAlbums(String query, int offset, int limit);
	CursorPageResponse<AlbumListResponse> getAlbumsByDate(Long cursorId, String cursorDate, int limit);
	CursorPageResponse<AlbumListResponse> getAlbumsByAverageScore(Long cursorId, String cursorScore, int limit);
	CursorPageResponse<AlbumListResponse> getAlbumsByReviewCount(Long cursorId, String cursorReviewCount, int limit);
}
