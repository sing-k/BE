package com.project.singk.domain.common.service.port;

public interface RedisRepository {
	void setValue(String key, String value, Long expiredTime);
	String getValue(String key);
	void deleteValue(String key);
}
