package com.project.singk.domain.alarm.infrastructure.jpa;

import com.project.singk.domain.alarm.infrastructure.entity.AlarmEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmJpaRepository extends JpaRepository<AlarmEntity,Long>{
}
