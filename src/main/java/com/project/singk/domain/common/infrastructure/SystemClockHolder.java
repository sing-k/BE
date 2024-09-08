package com.project.singk.domain.common.infrastructure;

import com.project.singk.domain.common.service.port.ClockHolder;
import org.springframework.stereotype.Component;

@Component
public class SystemClockHolder implements ClockHolder {
    @Override
    public long millis(){
        return System.currentTimeMillis();
    }
}
