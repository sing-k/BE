package com.project.singk.global.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseResponse<T> {
	@JsonIgnore
	AppHttpStatus status;

	int statusCode;
	String message;
	T data;

	public static BaseResponse<Void> ok() {
		return BaseResponse.<Void>builder()
			.statusCode(AppHttpStatus.OK.getStatusCode())
			.message(AppHttpStatus.OK.getMessage())
			.build();
	}

	public static <T> BaseResponse<T> ok(T data) {
		return BaseResponse.<T>builder()
			.statusCode(AppHttpStatus.OK.getStatusCode())
			.message(AppHttpStatus.OK.getMessage())
			.data(data)
			.build();
	}

	public static <T> BaseResponse<T> created(T data) {
		return BaseResponse.<T>builder()
			.statusCode(AppHttpStatus.CREATED.getStatusCode())
			.message(AppHttpStatus.CREATED.getMessage())
			.data(data)
			.build();
	}

	public static BaseResponse<Void> fail(ApiException e) {
		return BaseResponse.<Void>builder()
			.statusCode(e.getStatus().getStatusCode())
			.message(e.getStatus().getMessage())
			.build();
	}
	public static BaseResponse<Void> fail(AppHttpStatus status) {
		return BaseResponse.<Void>builder()
			.statusCode(status.getStatusCode())
			.message(status.getMessage())
			.build();
	}
	public static BaseResponse<Void> fail(AppHttpStatus status, String message) {
		return BaseResponse.<Void>builder()
			.statusCode(status.getStatusCode())
			.message(message)
			.build();
	}
}
