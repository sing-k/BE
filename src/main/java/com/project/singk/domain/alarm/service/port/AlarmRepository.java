package com.project.singk.domain.alarm.service.port;

import com.project.singk.domain.alarm.domain.Alarm;

public interface AlarmRepository {
    Alarm save(Alarm alarm);
    void deleteById(String id);
}
