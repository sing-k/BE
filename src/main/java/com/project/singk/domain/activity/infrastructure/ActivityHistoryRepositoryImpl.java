package com.project.singk.domain.activity.infrastructure;

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

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.project.singk.domain.activity.infrastructure.QActivityHistoryEntity.activityHistoryEntity;
import static com.project.singk.domain.album.infrastructure.entity.QAlbumEntity.albumEntity;
import static com.project.singk.domain.member.infrastructure.QMemberEntity.memberEntity;
import static com.project.singk.domain.review.infrastructure.QAlbumReviewEntity.albumReviewEntity;
import static java.util.stream.Collectors.groupingBy;

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
    public List<ActivityHistory> getActivityGraph(Long memberId) {
        return queryFactory.selectFrom(activityHistoryEntity)
                .where(activityHistoryEntity.member.id.eq(memberId))
                .orderBy(activityHistoryEntity.createdAt.asc())
                .fetch().stream()
                .map(ActivityHistoryEntity::toModel)
                .collect(Collectors.toList());
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

        // 각 날짜별 최종 값 구하기
        List<ActivityScore> result = new ArrayList<>();
        List<LocalDate> dates = start.datesUntil(end.plusDays(1)).toList(); // 호출 날짜 포함 7일

        int prev = 0;
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
