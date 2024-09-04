package com.project.singk.domain.alarm.infrastructure;

import com.project.singk.domain.alarm.service.port.EventCacheRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class EventCacheRepositoryImpl implements EventCacheRepository {
    private final Map<String, Map<String, Object>> eventCache = new HashMap<>();

    @Override
    public void saveEvent(String memberId, String eventId, Object data) {
        eventCache.computeIfAbsent(memberId, k -> new HashMap<>()).put(eventId, data);
    }

    @Override
    public Map<String, Object> findAllEventsAfter(String memberId, String lastEventId) {
        Map<String, Object> memberEvents = eventCache.getOrDefault(memberId, new HashMap<>());
        return memberEvents.entrySet().stream()
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void deleteEventsByMemberId(String memberId) {
        eventCache.remove(memberId);
    }
}
