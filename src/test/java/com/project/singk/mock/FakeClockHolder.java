package com.project.singk.mock;

import com.project.singk.domain.common.service.port.ClockHolder;
import com.project.singk.domain.common.service.port.UUIDHolder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FakeClockHolder implements ClockHolder {

	private final long mills;

    @Override
    public long millis() {
        return this.mills;
    }
}
