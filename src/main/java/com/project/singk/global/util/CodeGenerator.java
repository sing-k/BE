package com.project.singk.global.util;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class CodeGenerator {


	public static String createAuthenticationCode(int size) {
		StringBuilder code = new StringBuilder();
		long seed = System.currentTimeMillis();
		Random random = new Random(seed);

		for (int i = 0; i < size; i++) { // 인증코드 6자리
			code.append((random.nextInt(10)));
		}
		return code.toString();
	}
}
