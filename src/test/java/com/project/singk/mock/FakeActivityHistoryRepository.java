package com.project.singk.mock;

import com.project.singk.domain.activity.controller.request.ActivityDate;
import com.project.singk.domain.activity.domain.ActivityHistory;
import com.project.singk.domain.activity.domain.ActivityScore;
import com.project.singk.domain.activity.service.port.ActivityHistoryRepository;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class FakeActivityHistoryRepository implements ActivityHistoryRepository {
	private final List<ActivityHistory> data = Collections.synchronizedList(new ArrayList<>());

    @Override
    public ActivityHistory save(ActivityHistory activityHistory) {
        data.removeIf(item -> Objects.equals(item.getId(), activityHistory.getId()));
        data.add(activityHistory);
        return activityHistory;
    }

    @Override
    public List<ActivityScore> getActivityGraph(Long memberId, LocalDate start, LocalDate end, ActivityDate type) {
        return null;
    }

    @Override
    public Page<ActivityHistory> getActivityHistories(Long memberId, int offset, int limit) {
        return null;
    }

    @Override
    public List<ActivityScore> getActivityDailyGraph(Long memberId, LocalDate start, LocalDate end) {
        return null;
    }

    @Override
    public List<ActivityScore> getActivityWeeklyGraph(Long memberId, LocalDate start, LocalDate end) {
        return null;
    }

    @Override
    public List<ActivityScore> getActivityMonthlyGraph(Long memberId, LocalDate start, LocalDate end) {
        return null;
    }

}
