package com.project.singk.mock;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.project.singk.domain.common.service.port.RedisRepository;

public class FakeRedisRepository implements RedisRepository {
	private final Map<String, String> data = Collections.synchronizedMap(new HashMap<>());
	@Override
	public void setValue(String key, String value, Long expiredTime) {
		data.put(key, value);
	}

	@Override
	public String getValue(String key) {
		return data.get(key);
	}

	@Override
	public void deleteValue(String key) {
		data.remove(key);
	}
}
