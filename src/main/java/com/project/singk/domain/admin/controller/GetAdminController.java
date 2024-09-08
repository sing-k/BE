package com.project.singk.domain.admin.controller;

import com.project.singk.domain.admin.controller.port.AdminService;
import com.project.singk.domain.album.controller.response.AlbumListResponse;
import com.project.singk.domain.member.controller.response.MemberResponse;
import com.project.singk.global.api.BaseResponse;
import com.project.singk.global.api.CursorPageResponse;
import com.project.singk.global.api.OffsetPageResponse;
import com.project.singk.global.validate.Date;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/get")
public class GetAdminController {

	private final AdminService adminService;

    @GetMapping("/members")
    public BaseResponse<List<MemberResponse>> getMembers() {
        return BaseResponse.ok(adminService.getMembers());
    }

    @GetMapping("/albums")
    public BaseResponse<List<AlbumListResponse>> getAlbums() {
        return BaseResponse.ok(adminService.getAlbums());
    }

    @GetMapping("/albums/offset")
    public BaseResponse<OffsetPageResponse<AlbumListResponse>> getAlbumsWithOffset(
            @RequestParam("offset") int offset,
            @RequestParam("limit") int limit
    ) {
        return BaseResponse.ok(adminService.getAlbumsWithOffsetPaging(
                offset,
                limit
        ));
    }
    @GetMapping("/albums/cursor")
    public BaseResponse<CursorPageResponse<AlbumListResponse>> getAlbumsWithCursor(
            @RequestParam(value = "cursor-id", required = false) Long cursorId,
            @Date @RequestParam(value = "cursor-date", required = false) String cursorDate,
            @Range(min = 0, max = 50, message = "limit은 0에서 50사이의 값 이어야 합니다.") @RequestParam("limit") int limit
    ) {
        return BaseResponse.ok(adminService.getAlbumsWithCursorPaging(
                cursorId,
                cursorDate,
                limit
        ));
    }
}
