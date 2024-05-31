package com.project.singk.domain.member.infrastructure;

import org.springframework.stereotype.Component;

import com.project.singk.domain.common.service.port.RandomHolder;
import com.project.singk.domain.member.service.port.CodeGenerator;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CodeGeneratorImpl implements CodeGenerator {
	private final int CERTIFICATION_CODE_SIZE = 6;
	private final RandomHolder randomHolder;

	@Override
	public String createCertification() {
		StringBuilder code = new StringBuilder();

		for (int i = 0; i < CERTIFICATION_CODE_SIZE; i++) {
			code.append(randomHolder.randomInt(10));
		}

		return code.toString();
	}
}
