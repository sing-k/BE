package com.project.singk.mock;

import com.project.singk.domain.activity.domain.ActivityHistory;
import com.project.singk.domain.activity.service.port.ActivityHistoryRepository;

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

}
