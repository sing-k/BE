package com.project.singk.global.api;

import java.nio.file.AccessDeniedException;
import java.util.Iterator;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	public BaseResponse<Void> runtimeExceptionHandler(RuntimeException e) {
		return BaseResponse.fail(AppHttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
	}
	@ExceptionHandler(ApiException.class)
	public BaseResponse<Void> apiExceptionHandler(ApiException e) {
		return BaseResponse.fail(e);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public BaseResponse<Void> accessDeniedExceptionHandler(AccessDeniedException e) {
		return BaseResponse.fail(AppHttpStatus.FORBIDDEN);
	}
	@ExceptionHandler(ConstraintViolationException.class)
	public BaseResponse<Void> constraintViolationExceptionHandler(ConstraintViolationException e) {
		StringBuilder error = new StringBuilder();
		for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
			error.append(violation.getMessage());
		}
		return BaseResponse.fail(AppHttpStatus.BAD_REQUEST, error.toString());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public BaseResponse<Void> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
		return BaseResponse.fail(AppHttpStatus.BAD_REQUEST, e.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
	}
}
