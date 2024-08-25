package com.project.singk.domain.alarm.infrastructure;

import com.project.singk.domain.alarm.domain.Alarm;
import com.project.singk.domain.alarm.infrastructure.entity.AlarmEntity;
import com.project.singk.domain.alarm.infrastructure.jpa.AlarmJpaRepository;
import com.project.singk.domain.alarm.service.port.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AlarmRepositoryImpl implements AlarmRepository {

    private final AlarmJpaRepository alarmJpaRepository;

    @Override
    public Alarm save(Alarm alarm){ return alarmJpaRepository.save(AlarmEntity.from(alarm)).toModel();}

    @Override
    public void deleteById(Long id){alarmJpaRepository.deleteById(id);}
}
