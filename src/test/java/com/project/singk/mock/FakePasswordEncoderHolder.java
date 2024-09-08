package com.project.singk.mock;

import com.project.singk.domain.member.service.port.PasswordEncoderHolder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FakePasswordEncoderHolder implements PasswordEncoderHolder {
	private final String encodedPassword;

	@Override
	public String encode(String password) {
		return encodedPassword;
	}
}
