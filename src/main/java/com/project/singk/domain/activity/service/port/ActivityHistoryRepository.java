package com.project.singk.domain.activity.service.port;

import com.project.singk.domain.activity.domain.ActivityHistory;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ActivityHistoryRepository {
    ActivityHistory save(ActivityHistory activityHistory);
    List<ActivityHistory> getActivityGraph(Long memberId);
    Page<ActivityHistory> getActivityHistories(Long memberId, int offset, int limit);
}
