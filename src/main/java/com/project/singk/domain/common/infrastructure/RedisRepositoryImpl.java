package com.project.singk.domain.common.infrastructure;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.project.singk.domain.common.service.port.RedisRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisRepositoryImpl implements RedisRepository {

	private final RedisTemplate<String, String> redisTemplate;

	@Override
	public void setValue(String key, String value, Long expiredTime) {
		redisTemplate.opsForValue().set(key, value, expiredTime, TimeUnit.MILLISECONDS);
	}

	@Override
	public String getValue(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	@Override
	public void deleteValue(String key) {
		redisTemplate.delete(key);
	}
}
