package com.project.singk.domain.activity.service.port;

import com.project.singk.domain.activity.domain.ActivityHistory;

public interface ActivityHistoryRepository {
    ActivityHistory save(ActivityHistory activityHistory);
}
