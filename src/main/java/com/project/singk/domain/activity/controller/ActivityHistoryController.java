package com.project.singk.domain.activity.controller;

import com.project.singk.domain.activity.controller.port.ActivityHistoryService;
import com.project.singk.domain.activity.controller.response.ActivityGraphResponse;
import com.project.singk.domain.activity.controller.response.ActivityHistoryResponse;
import com.project.singk.domain.member.controller.port.AuthService;
import com.project.singk.global.api.BaseResponse;
import com.project.singk.global.api.PageResponse;
import com.project.singk.global.validate.Date;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/activity")
public class ActivityHistoryController {
    private final ActivityHistoryService activityHistoryService;
    private final AuthService authService;

    @GetMapping("/graph")
    public BaseResponse<List<ActivityGraphResponse>> getActivityHistoryGraph() {
        return BaseResponse.ok(activityHistoryService.getActivityGraph(
                authService.getLoginMemberId()
        ));
    }

    @GetMapping("/list")
    public BaseResponse<PageResponse<ActivityHistoryResponse>> getActivityHistories(
            @Range(min = 0, max = 1000, message = "offset은 0에서 1000사이의 값 이어야 합니다.") @RequestParam("offset") int offset,
            @Range(min = 0, max = 50, message = "limit은 0에서 50사이의 값 이어야 합니다.") @RequestParam("limit") int limit
    ) {
        return BaseResponse.ok(activityHistoryService.getActivityHistories(
                authService.getLoginMemberId(),
                offset,
                limit
        ));
    }

    @GetMapping("/daily/graph")
    public BaseResponse<List<ActivityGraphResponse>> getDailyActivityGraph(
            @Date @RequestParam("startDate") String startDate,
            @Date @RequestParam("endDate") String endDate
    ) {
        return BaseResponse.ok(activityHistoryService.getDailyActivityGraph(
                authService.getLoginMemberId(),
                startDate,
                endDate
        ));
    }
}
