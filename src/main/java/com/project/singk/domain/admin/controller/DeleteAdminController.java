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
@RequestMapping("/api/admin/delete")
public class DeleteAdminController {

	private final AdminService adminService;

    @DeleteMapping("/members/{memberId}")
    public BaseResponse<Void> deleteMember(
            @PathVariable(name = "memberId") Long memberId
    ) {
        adminService.deleteMember(memberId);
        return BaseResponse.ok();
    }
}
