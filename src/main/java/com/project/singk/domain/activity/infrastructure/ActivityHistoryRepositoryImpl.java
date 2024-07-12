package com.project.singk.domain.activity.infrastructure;

import com.project.singk.domain.activity.domain.ActivityHistory;
import com.project.singk.domain.activity.service.port.ActivityHistoryRepository;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.project.singk.domain.activity.infrastructure.QActivityHistoryEntity.activityHistoryEntity;
import static com.project.singk.domain.album.infrastructure.entity.QAlbumEntity.albumEntity;
import static com.project.singk.domain.member.infrastructure.QMemberEntity.memberEntity;
import static com.project.singk.domain.review.infrastructure.QAlbumReviewEntity.albumReviewEntity;

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

}
