package com.project.singk.domain.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthType {
	GOOGLE("google", ""),
	NAVER("naver", "response"),
	KAKAO("kakao", "kakao_account");

	private final String id;
	private final String attr;
}
