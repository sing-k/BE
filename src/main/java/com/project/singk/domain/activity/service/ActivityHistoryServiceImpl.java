package com.project.singk.domain.activity.service;

import com.project.singk.domain.activity.controller.port.ActivityHistoryService;
import com.project.singk.domain.activity.controller.request.ActivityDate;
import com.project.singk.domain.activity.controller.response.ActivityGraphResponse;
import com.project.singk.domain.activity.controller.response.ActivityHistoryResponse;
import com.project.singk.domain.activity.domain.ActivityHistory;
import com.project.singk.domain.activity.service.port.ActivityHistoryRepository;
import com.project.singk.global.api.OffsetPageResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Builder
@RequiredArgsConstructor
@Transactional
public class ActivityHistoryServiceImpl implements ActivityHistoryService {

    private final ActivityHistoryRepository activityHistoryRepository;
    @Override
    public List<ActivityGraphResponse> getActivityGraph(Long memberId, String startDate, String endDate, String dateType) {
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        ActivityDate type = ActivityDate.valueOf(dateType);

        return activityHistoryRepository.getActivityGraph(
                        memberId,
                        start,
                        end,
                        type
                ).stream()
                .map(ActivityGraphResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public OffsetPageResponse<ActivityHistoryResponse> getActivityHistories(Long memberId, int offset, int limit) {
        Page<ActivityHistory> activityHistories = activityHistoryRepository.getActivityHistories(memberId, offset, limit);

        return OffsetPageResponse.of(
                offset,
                limit,
                (int) activityHistories.getTotalElements(),
                activityHistories.getContent().stream()
                        .map(ActivityHistoryResponse::from)
                        .toList()
        );
    }

}
