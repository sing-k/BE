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
@RequestMapping("/api/admin/get")
public class GetAdminController {

	private final AdminService adminService;

    @GetMapping("/members")
    public BaseResponse<List<MemberResponse>> getMembers() {
        return BaseResponse.ok(adminService.getMembers());
    }
}
