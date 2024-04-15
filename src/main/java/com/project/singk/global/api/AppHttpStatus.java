package com.project.singk.global.api;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AppHttpStatus {
	/**
	 * 200
	 */
	OK(200, HttpStatus.OK, "요청이 정상적으로 수행되었습니다."),
	CREATED(201, HttpStatus.CREATED, "리소스를 생성하였습니다."),

	/**
	 * 403
	 */
	FORBIDDEN(403, HttpStatus.FORBIDDEN, "권한이 없습니다.");

	private final int statusCode;
	private final HttpStatus httpStatus;
	private final String message;
}
