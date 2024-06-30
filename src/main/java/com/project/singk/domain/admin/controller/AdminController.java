package com.project.singk.domain.admin.controller;

import com.project.singk.domain.admin.controller.port.AdminService;
import com.project.singk.domain.album.controller.response.AlbumDetailResponse;
import com.project.singk.domain.member.controller.response.MemberResponse;
import com.project.singk.global.api.BaseResponse;
import com.project.singk.global.api.PageResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/")
public class AdminController {

	private final AdminService adminService;

	@PostMapping("/create/albums")
	public BaseResponse<PageResponse<AlbumDetailResponse>> createAlbums(
		@RequestParam(value = "query", required = false) String query,
		@Range(min = 0, max = 1000, message = "offset은 0에서 1000사이의 값 이어야 합니다.") @RequestParam("offset") int offset,
		@Range(min = 0, max = 50, message = "limit은 0에서 50사이의 값 이어야 합니다.") @RequestParam("limit") int limit
	) {
		return BaseResponse.ok(adminService.createAlbums(query, offset, limit));
	}

    @DeleteMapping("/delete/members/{memberId}")
    public BaseResponse<Void> deleteMember(
            @PathVariable(name = "memberId") Long memberId
    ) {
        adminService.deleteMember(memberId);
        return BaseResponse.ok();
    }

    @GetMapping("/get/members")
    public BaseResponse<List<MemberResponse>> getMembers() {
        return BaseResponse.ok(adminService.getMembers());
    }
}
