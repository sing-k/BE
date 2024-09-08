package com.project.singk.domain.activity.controller.port;

import com.project.singk.domain.activity.controller.response.ActivityGraphResponse;
import com.project.singk.domain.activity.controller.response.ActivityHistoryResponse;
import com.project.singk.domain.activity.domain.ActivityHistory;
import com.project.singk.domain.activity.domain.ActivityType;
import com.project.singk.global.api.OffsetPageResponse;

import java.util.List;

public interface ActivityHistoryService {
    List<ActivityGraphResponse> getActivityGraph(Long memberId, String startDate, String endDate, String dateType);
    OffsetPageResponse<ActivityHistoryResponse> getActivityHistories(Long memberId, int offset, int limit);
}
