package com.project.singk.mock;

import com.project.singk.domain.activity.controller.request.ActivityDate;
import com.project.singk.domain.activity.domain.ActivityHistory;
import com.project.singk.domain.activity.domain.ActivityScore;
import com.project.singk.domain.activity.service.port.ActivityHistoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

public class FakeActivityHistoryRepository implements ActivityHistoryRepository {
	private final List<ActivityHistory> data = Collections.synchronizedList(new ArrayList<>());

    @Override
    public ActivityHistory save(ActivityHistory activityHistory) {
        data.removeIf(item -> Objects.equals(item.getId(), activityHistory.getId()));
        data.add(activityHistory);
        return activityHistory;
    }

    @Override
    public List<ActivityHistory> saveAll(List<ActivityHistory> activityHistories) {
        return activityHistories.stream().map(this::save).toList();
    }

    @Override
    public List<ActivityScore> getActivityGraph(Long memberId, LocalDate start, LocalDate end, ActivityDate type) {
        List<ActivityHistory> accumulatedHistories = getAccumulatedHistories(memberId);
        List<LocalDate> dates = getDates(start, end, type);
        return getActivityScores(accumulatedHistories, dates);
    }

    @Override
    public Page<ActivityHistory> getActivityHistories(Long memberId, int offset, int limit) {
        List<ActivityHistory> activityHistories = data.stream()
                .filter(item -> item.getMember().getId().equals(memberId))
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .skip(offset)
                .limit(limit)
                .toList();

        Pageable pageable = Pageable.ofSize(limit);

        return new PageImpl<>(activityHistories, pageable, data.size());
    }

    private List<ActivityHistory> getAccumulatedHistories(Long memberId) {

        List<ActivityHistory> activityHistories = data.stream()
                .filter(item -> item.getMember().getId().equals(memberId))
                .sorted(Comparator.comparing(ActivityHistory::getCreatedAt))
                .toList();

        int accumulateScore = 0;
        List<ActivityHistory> accumulatedHistories = new ArrayList<>();
        for (ActivityHistory activityHistory : activityHistories) {
            accumulateScore += activityHistory.getScore();
            accumulatedHistories.add(activityHistory.setAccumulatedScore(accumulateScore));
        }

        return accumulatedHistories;
    }

    private List<LocalDate> getDates(LocalDate start, LocalDate end, ActivityDate type) {
        if (type.equals(ActivityDate.DAILY)) {
            return start.datesUntil(end.plusDays(1)).toList();
        } else if (type.equals(ActivityDate.WEEKLY)) {
            // 각 주의 일요일
            List<LocalDate> dates = new ArrayList<>(start.datesUntil(end.plusDays(1))
                    .filter(date -> date.getDayOfWeek() == DayOfWeek.SUNDAY)
                    .toList());

            if (end.getDayOfWeek() != DayOfWeek.SUNDAY) dates.add(end);
            dates.add(0, start);

            return dates;
        } else if (type.equals(ActivityDate.MONTHLY)) {
            // 각 달의 마지막 날
            List<LocalDate> dates = new ArrayList<>(start.datesUntil(end.plusDays(1))
                    .filter(date -> date.equals(YearMonth.from(date).atEndOfMonth()))
                    .toList());

            if (!end.equals(YearMonth.from(end).atEndOfMonth())) dates.add(end);
            dates.add(0, start);

            return dates;
        }

        return null;
    }

    private List<ActivityScore> getActivityScores(List<ActivityHistory> accumulatedHistories, List<LocalDate> dates) {
        int prev = 0;
        List<ActivityScore> result = new ArrayList<>();
        for (LocalDate date : dates) {
            List<ActivityHistory> histories = accumulatedHistories.stream()
                    .filter(score -> score.getCreatedAt().isAfter(date.atStartOfDay()) && score.getCreatedAt().isBefore(date.plusDays(1).atStartOfDay()))
                    .sorted(Comparator.comparing(ActivityHistory::getCreatedAt).reversed())
                    .toList();

            if (!histories.isEmpty()) prev = histories.get(0).getScore();

            result.add(ActivityScore.from(prev, date));
        }

        return result;
    }
}
