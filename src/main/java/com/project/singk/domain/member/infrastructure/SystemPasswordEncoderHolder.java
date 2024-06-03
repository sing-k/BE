package com.project.singk.domain.member.infrastructure;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.project.singk.domain.member.service.port.PasswordEncoderHolder;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SystemPasswordEncoderHolder implements PasswordEncoderHolder {

	private final PasswordEncoder passwordEncoder;
	@Override
	public String encode(String password) {
		return passwordEncoder.encode(password);
	}
}
