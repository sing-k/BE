package com.project.singk.domain.alarm.service.port;

import com.project.singk.domain.alarm.domain.Alarm;
import org.springframework.data.domain.Page;

public interface AlarmRepository {
    Alarm save(Alarm alarm);
    Page<Alarm> findAllByMemberId(Long memberId, int offset, int limit);
    void deleteById(Long id);
    Long countIsReadFalseByMemberId(Long memberId);
}
