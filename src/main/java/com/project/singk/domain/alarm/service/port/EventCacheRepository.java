package com.project.singk.domain.alarm.service.port;

import java.util.Map;

public interface EventCacheRepository {
    void saveEvent(String memberId, String eventId, Object data);
    Map<String, Object> findAllEventsAfter(String memberId, String lastEventId);
    void deleteEventsByMemberId(String memberId);
}
