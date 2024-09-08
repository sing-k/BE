package com.project.singk.domain.admin.controller.port;

import com.project.singk.domain.activity.controller.response.ActivityHistoryResponse;
import com.project.singk.domain.album.controller.response.AlbumDetailResponse;
import com.project.singk.domain.album.controller.response.AlbumListResponse;
import com.project.singk.domain.member.controller.response.MemberResponse;
import com.project.singk.global.api.CursorPageResponse;
import com.project.singk.global.api.OffsetPageResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface AdminService {
    OffsetPageResponse<AlbumDetailResponse> createAlbums(String query, int offset, int limit);
    CompletableFuture<OffsetPageResponse<AlbumDetailResponse>> createAlbumsWithAsync(String query, int offset, int limit);
    void deleteMember(Long memberId);
    List<MemberResponse> getMembers();
    List<AlbumListResponse> getAlbums();
    OffsetPageResponse<AlbumListResponse> getAlbumsWithOffsetPaging(int offset, int limit);
    CursorPageResponse<AlbumListResponse> getAlbumsWithCursorPaging(Long cursorId, String cursorDate, int limit);
    List<ActivityHistoryResponse> createActivityHistories(Long memberId, String startDate, String endDate, int count);
}
