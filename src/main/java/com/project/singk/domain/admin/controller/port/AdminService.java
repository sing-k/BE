package com.project.singk.domain.admin.controller.port;

import com.project.singk.domain.album.controller.response.AlbumDetailResponse;
import com.project.singk.domain.member.controller.response.MemberResponse;
import com.project.singk.global.api.PageResponse;

import java.util.List;

public interface AdminService {
    PageResponse<AlbumDetailResponse> createAlbums(String query, int offset, int limit);
    void deleteMember(Long memberId);
    List<MemberResponse> getMembers();
}
