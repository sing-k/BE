package com.project.singk.domain.common.infrastructure;

import java.util.Random;

import org.springframework.stereotype.Component;

import com.project.singk.domain.common.service.port.RandomHolder;

@Component
public class SystemRandomHolder implements RandomHolder {
	private final Random random;

	public SystemRandomHolder() {
		this.random = new Random(System.currentTimeMillis());
	}

	@Override
	public int randomInt(int bound) {
		return random.nextInt(bound);
	}
}
