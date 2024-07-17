package com.project.singk.domain.activity.service;

import com.project.singk.domain.activity.controller.port.ActivityHistoryService;
import com.project.singk.domain.activity.controller.response.ActivityGraphResponse;
import com.project.singk.domain.activity.controller.response.ActivityHistoryResponse;
import com.project.singk.domain.activity.domain.ActivityHistory;
import com.project.singk.domain.activity.service.port.ActivityHistoryRepository;
import com.project.singk.global.api.PageResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Builder
@RequiredArgsConstructor
@Transactional
public class ActivityHistoryServiceImpl implements ActivityHistoryService {

    private final ActivityHistoryRepository activityHistoryRepository;
    @Override
    public List<ActivityGraphResponse> getActivityGraph(Long memberId) {
        AtomicInteger accumulatedScore = new AtomicInteger(0);
        return activityHistoryRepository.getActivityGraph(memberId).stream()
                .map(activity -> {
                    accumulatedScore.addAndGet(activity.getScore());

                    return ActivityGraphResponse.builder()
                            .date(activity.getCreatedAt().toLocalDate())
                            .score(accumulatedScore.get())
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public PageResponse<ActivityHistoryResponse> getActivityHistories(Long memberId, int offset, int limit) {
        Page<ActivityHistory> activityHistories = activityHistoryRepository.getActivityHistories(memberId, offset, limit);

        return PageResponse.of(
                offset,
                limit,
                (int) activityHistories.getTotalElements(),
                activityHistories.getContent().stream()
                        .map(ActivityHistoryResponse::from)
                        .toList()
        );
    }

    @Override
    public List<ActivityGraphResponse> getDailyActivityGraph(Long memberId, String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        return activityHistoryRepository.getActivityDailyGraph(
                memberId,
                start,
                end
            ).stream()
            .map(ActivityGraphResponse::from)
            .collect(Collectors.toList());
    }

}
