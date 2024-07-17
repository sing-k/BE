package com.project.singk.domain.activity.service.port;

import com.project.singk.domain.activity.domain.ActivityHistory;
import com.project.singk.domain.activity.domain.ActivityScore;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface ActivityHistoryRepository {
    ActivityHistory save(ActivityHistory activityHistory);
    List<ActivityHistory> getActivityGraph(Long memberId);
    Page<ActivityHistory> getActivityHistories(Long memberId, int offset, int limit);

    List<ActivityScore> getActivityDailyGraph(Long memberId, LocalDate start, LocalDate end);
}
