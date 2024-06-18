package com.project.singk.domain.activity.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityHistoryJpaRepository extends JpaRepository<ActivityHistoryEntity, Long> {
}
