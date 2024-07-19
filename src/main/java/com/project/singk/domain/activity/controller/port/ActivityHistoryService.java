package com.project.singk.domain.activity.controller.port;

import com.project.singk.domain.activity.controller.response.ActivityGraphResponse;
import com.project.singk.domain.activity.controller.response.ActivityHistoryResponse;
import com.project.singk.global.api.PageResponse;

import java.util.List;

public interface ActivityHistoryService {
    List<ActivityGraphResponse> getActivityGraph(Long memberId, String startDate, String endDate, String dateType);
    PageResponse<ActivityHistoryResponse> getActivityHistories(Long memberId, int offset, int limit);
    List<ActivityGraphResponse> getDailyActivityGraph(Long memberId, String startDate, String endDate);
    List<ActivityGraphResponse> getWeeklyActivityGraph(Long memberId, String startDate, String endDate);

    List<ActivityGraphResponse> getMonthlyActivityGraph(Long memberId, String startDate, String endDate);
}
