package com.project.singk.domain.common.infrastructure;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.project.singk.domain.common.service.port.UUIDHolder;

@Component
public class SystemUUIDHolder implements UUIDHolder {
	@Override
	public String randomUUID() {
		return UUID.randomUUID().toString();
	}
}
