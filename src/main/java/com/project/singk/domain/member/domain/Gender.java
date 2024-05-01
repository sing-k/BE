package com.project.singk.domain.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {
	MALE("남성"),
	FEMALE("여성"),
	NONE("선택안함");

	private final String name;
}
