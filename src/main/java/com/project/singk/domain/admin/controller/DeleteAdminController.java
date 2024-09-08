package com.project.singk.domain.admin.controller;

import com.project.singk.domain.admin.controller.port.AdminService;
import com.project.singk.global.api.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
