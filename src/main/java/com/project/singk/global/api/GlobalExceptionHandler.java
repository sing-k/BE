package com.project.singk.global.api;

import java.nio.file.AccessDeniedException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ApiException.class)
	public BaseResponse<Void> apiExceptionHandler(ApiException e) {
		return BaseResponse.fail(e);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public BaseResponse<Void> accessDeniedExceptionHandler(AccessDeniedException e) {
		return BaseResponse.fail(new ApiException(AppHttpStatus.FORBIDDEN));
	}
}
