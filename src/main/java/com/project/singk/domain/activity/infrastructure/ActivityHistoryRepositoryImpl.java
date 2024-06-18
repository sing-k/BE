package com.project.singk.domain.activity.infrastructure;

import com.project.singk.domain.activity.domain.ActivityHistory;
import com.project.singk.domain.activity.service.port.ActivityHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ActivityHistoryRepositoryImpl implements ActivityHistoryRepository {

    private final ActivityHistoryJpaRepository activityHistoryJpaRepository;

    @Override
    public ActivityHistory save(ActivityHistory activityHistory) {
        return activityHistoryJpaRepository.save(ActivityHistoryEntity.from(activityHistory)).toModel();
    }
}
