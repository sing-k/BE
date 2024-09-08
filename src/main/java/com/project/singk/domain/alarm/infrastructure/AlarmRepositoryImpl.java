package com.project.singk.domain.alarm.infrastructure;

import com.project.singk.domain.alarm.domain.Alarm;
import com.project.singk.domain.alarm.infrastructure.entity.AlarmEntity;
import com.project.singk.domain.alarm.infrastructure.jpa.AlarmJpaRepository;
import com.project.singk.domain.alarm.service.port.AlarmRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.project.singk.domain.alarm.infrastructure.entity.QAlarmEntity.alarmEntity;

@Repository
@RequiredArgsConstructor
public class AlarmRepositoryImpl implements AlarmRepository {

    private final AlarmJpaRepository alarmJpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public Alarm save(Alarm alarm){ return alarmJpaRepository.save(AlarmEntity.from(alarm)).toModel();}

    @Override
    public Page<Alarm> findAllByMemberId(Long memberId, int offset, int limit) {
        List<Alarm> alarms = queryFactory.selectFrom(alarmEntity)
                .where(alarmEntity.receiver.id.eq(memberId))
                .orderBy(alarmEntity.createdAt.desc())
                .offset(offset)
                .limit(limit)
                .fetch().stream()
                .map(AlarmEntity::toModel)
                .toList();

        Long count = queryFactory.select(alarmEntity.count())
                .from(alarmEntity)
                .where(alarmEntity.receiver.id.eq(memberId))
                .fetchOne();

        Pageable pageable = PageRequest.ofSize(limit);

        return new PageImpl<>(alarms, pageable, count);
    }

    @Override
    public void deleteById(Long id){alarmJpaRepository.deleteById(id);}

    @Override
    public Long countIsReadFalseByMemberId(Long memberId) {
        return queryFactory.select(alarmEntity.count())
                .from(alarmEntity)
                .where(alarmEntity.isRead.isFalse()
                        .and(alarmEntity.receiver.id.eq(memberId)))
                .fetchOne();
    }
}
