package com.project.singk.domain.album.controller.port;

import com.project.singk.domain.album.controller.request.AlbumSort;
import com.project.singk.domain.album.controller.response.AlbumDetailResponse;
import com.project.singk.domain.album.controller.response.AlbumListResponse;
import com.project.singk.global.api.Page;

public interface AlbumService {
	AlbumDetailResponse getAlbum(String id);
	Page<AlbumListResponse> searchAlbums(String query, int offset, int limit);
	Page<AlbumListResponse> getPreviewAlbumsByAlbumSort(AlbumSort sort, int offset, int limit);
}
