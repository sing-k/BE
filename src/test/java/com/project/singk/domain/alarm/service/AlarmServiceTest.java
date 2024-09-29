package com.project.singk.domain.alarm.service;

import com.project.singk.domain.alarm.infrastructure.EmitterRepositoryImpl;
import com.project.singk.mock.TestClockHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AlarmServiceTest {

    private AlarmServiceImpl alarmService;

    @BeforeEach
    void init(){
        FakeAlarmRepository fakeAlarmRepository = new FakeAlarmRepository();
        EmitterRepositoryImpl emitterRepository = new EmitterRepositoryImpl();
        TestClockHolder clockHolder = new TestClockHolder(1234567890L);
        this.alarmService = AlarmServiceImpl.builder()
                .alarmRepository(fakeAlarmRepository)
                .emitterRepository(emitterRepository)
                .clockHolder(clockHolder)
                .build();

    }

    @Test
    void username과_lastEventId로_서버에_구독할_수_있다(){

    }

    @Test
    void 클라이언트에게_알람을_보낼_수_있다(){

    }

    @Test
    void id를_사용하여_알람을_삭제할_수_있다(){

    }
}
