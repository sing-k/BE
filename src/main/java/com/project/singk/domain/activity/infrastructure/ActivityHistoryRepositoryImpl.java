package com.project.singk.domain.activity.infrastructure;

import com.project.singk.domain.activity.controller.request.ActivityDate;
import com.project.singk.domain.activity.domain.ActivityHistory;
import com.project.singk.domain.activity.domain.ActivityScore;
import com.project.singk.domain.activity.service.port.ActivityHistoryRepository;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

import static com.project.singk.domain.activity.infrastructure.QActivityHistoryEntity.activityHistoryEntity;


@Repository
@RequiredArgsConstructor
public class ActivityHistoryRepositoryImpl implements ActivityHistoryRepository {

    private final ActivityHistoryJpaRepository activityHistoryJpaRepository;
    private final JPAQueryFactory queryFactory;
    @Override
    public ActivityHistory save(ActivityHistory activityHistory) {
        return activityHistoryJpaRepository.save(ActivityHistoryEntity.from(activityHistory)).toModel();
    }

    @Override
    public List<ActivityHistory> saveAll(List<ActivityHistory> activityHistories) {
        return activityHistoryJpaRepository.saveAll(activityHistories.stream()
                .map(ActivityHistoryEntity::from)
                .toList()).stream()
                .map(ActivityHistoryEntity::toModel)
                .toList();
    }

    @Override
    public List<ActivityScore> getActivityGraph(Long memberId, LocalDate start, LocalDate end, ActivityDate type) {
        // 누적합 구하기
        List<ActivityHistory> accumulatedHistories = getAccumulatedHistories(memberId);

        // 날짜 구하기
        List<LocalDate> dates = getDates(start, end, type);

        return getActivityScores(accumulatedHistories, dates);
    }

    @Override
    public Page<ActivityHistory> getActivityHistories(Long memberId, int offset, int limit) {
        List<ActivityHistory> activityHistories = queryFactory.selectFrom(activityHistoryEntity)
                .where(activityHistoryEntity.member.id.eq(memberId))
                .offset(offset)
                .limit(limit)
                .orderBy(activityHistoryEntity.createdAt.desc())
                .fetch().stream()
                .map(ActivityHistoryEntity::toModel)
                .toList();

        JPAQuery<Long> count = queryFactory.select(activityHistoryEntity.count())
                .from(activityHistoryEntity)
                .where(activityHistoryEntity.member.id.eq(memberId));

        Pageable pageable = PageRequest.ofSize(limit);

        return PageableExecutionUtils.getPage(activityHistories, pageable, count::fetchOne);
    }

    @Override
    public List<ActivityScore> getActivityDailyGraph(Long memberId, LocalDate start, LocalDate end) {
        // 누적합 구하기
        List<ActivityHistory> accumulatedHistories = getAccumulatedHistories(memberId);

        // 각 날짜별 최종 값 구하기
        List<LocalDate> dates = start.datesUntil(end.plusDays(1)).toList(); // 호출 날짜 포함 7일

        return getActivityScores(accumulatedHistories, dates);
    }

    @Override
    public List<ActivityScore> getActivityWeeklyGraph(Long memberId, LocalDate start, LocalDate end) {
        // 누적합 구하기
        List<ActivityHistory> accumulatedHistories = getAccumulatedHistories(memberId);

        // 각 주별 최종 값 구하기 - 각 주의 일요일
        List<LocalDate> dates = new ArrayList<>(start.datesUntil(end.plusDays(1))
                .filter(date -> date.getDayOfWeek() == DayOfWeek.SUNDAY)
                .toList());

        if (end.getDayOfWeek() != DayOfWeek.SUNDAY) dates.add(end);

        return getActivityScores(accumulatedHistories, dates);
    }

    @Override
    public List<ActivityScore> getActivityMonthlyGraph(Long memberId, LocalDate start, LocalDate end) {
        // 누적합 구하기
        List<ActivityHistory> accumulatedHistories = getAccumulatedHistories(memberId);

        // 각 달별 최종 값 구하기 - 각 달의 마지막 날
        List<LocalDate> dates = new ArrayList<>(start.datesUntil(end.plusDays(1))
                .filter(date -> date.equals(YearMonth.from(date).atEndOfMonth()))
                .toList());

        if (!end.equals(YearMonth.from(end).atEndOfMonth())) dates.add(end);

        return getActivityScores(accumulatedHistories, dates);
    }

    private List<ActivityHistory> getAccumulatedHistories(Long memberId) {
        List<ActivityHistory> activityHistories = queryFactory.select(activityHistoryEntity)
                .from(activityHistoryEntity)
                .where(activityHistoryEntity.member.id.eq(memberId))
                .orderBy(activityHistoryEntity.createdAt.asc())
                .fetch().stream()
                .map(ActivityHistoryEntity::toModel)
                .toList();

        // 누적합 구하기
        int accumulateScore = 0;
        List<ActivityHistory> accumulatedHistories = new ArrayList<>();
        for (ActivityHistory activityHistory : activityHistories) {
            accumulateScore += activityHistory.getScore();
            accumulatedHistories.add(activityHistory.setAccumulatedScore(accumulateScore));
        }

        return accumulatedHistories;
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

    private List<LocalDate> getDates(LocalDate start, LocalDate end, ActivityDate type) {
        if (type.equals(ActivityDate.DAILY)) {
            return start.datesUntil(end.plusDays(1)).toList();
        } else if (type.equals(ActivityDate.WEEKLY)) {
            // 각 주의 일요일
            List<LocalDate> dates = new ArrayList<>(start.datesUntil(end.plusDays(1))
                    .filter(date -> date.getDayOfWeek() == DayOfWeek.SUNDAY)
                    .toList());

            if (end.getDayOfWeek() != DayOfWeek.SUNDAY) dates.add(end);

            return dates;
        } else if (type.equals(ActivityDate.MONTHLY)) {
            // 각 달의 마지막 날
            List<LocalDate> dates = new ArrayList<>(start.datesUntil(end.plusDays(1))
                    .filter(date -> date.equals(YearMonth.from(date).atEndOfMonth()))
                    .toList());

            if (!end.equals(YearMonth.from(end).atEndOfMonth())) dates.add(end);

            return dates;
        }

        return null;
    }
}
