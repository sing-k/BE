package com.project.singk.global.util;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisUtil {

	private final RedisTemplate<String, String> redisTemplate;

	public void setValue(String key, String value, Long expiredTime) {
		redisTemplate.opsForValue().set(key, value, expiredTime, TimeUnit.MILLISECONDS);
	}

	public String getValue(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	public void deleteValue(String key) {
		redisTemplate.delete(key);
	}
}
