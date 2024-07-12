package com.project.singk.domain.activity.controller.port;

import com.project.singk.domain.activity.controller.response.ActivityGraphResponse;
import com.project.singk.domain.activity.controller.response.ActivityHistoryResponse;
import com.project.singk.global.api.PageResponse;

import java.util.List;

public interface ActivityHistoryService {
    List<ActivityGraphResponse> getActivityGraph(Long memberId);
    PageResponse<ActivityHistoryResponse> getActivityHistories(Long memberId, int offset, int limit);
}
