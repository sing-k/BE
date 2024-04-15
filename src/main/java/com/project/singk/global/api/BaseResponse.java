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
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
			.status(AppHttpStatus.OK)
			.build();
	}

	public static <T> BaseResponse<T> ok(T data) {
		return BaseResponse.<T>builder()
			.status(AppHttpStatus.OK)
			.data(data)
			.build();
	}

	public static <T> BaseResponse<T> created(T data) {
		return BaseResponse.<T>builder()
			.status(AppHttpStatus.CREATED)
			.data(data)
			.build();
	}

	public static BaseResponse<Void> fail(ApiException e) {
		return BaseResponse.<Void>builder()
			.status(e.getStatus())
			.build();
	}

	private static <T> CustomBaseResponseBuilder<T> builder() {
		return new CustomBaseResponseBuilder<>();
	}

	private static class CustomBaseResponseBuilder<T> extends BaseResponseBuilder<T> {
		@Override
		public BaseResponse<T> build() {
			BaseResponse<T> baseResponse = super.build();

			AppHttpStatus status = baseResponse.status;
			baseResponse.statusCode = status.getStatusCode();
			baseResponse.message = status.getMessage();
			return baseResponse;
		}
	}
}
