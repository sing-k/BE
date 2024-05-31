package com.project.singk.mock;

import com.project.singk.domain.common.service.port.UUIDHolder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FakeUUIDHolder implements UUIDHolder {

	private final String uuid;

	@Override
	public String randomUUID() {
		return this.uuid;
	}
}
