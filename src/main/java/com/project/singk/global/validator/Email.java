package com.project.singk.global.validator;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({FIELD, PARAMETER}) // 필드에만 어노테이션 선언 가능
@Retention(RetentionPolicy.RUNTIME) // 런타임 동안에만 어노테이션
@Constraint(validatedBy = EmailValidator.class)
public @interface Email {
	//  JSR-303 표준 어노테이션들이 갖는 공통 속성
	String message() default "올바르지 이메일 않은 형식입니다."; // 유효성 검증에 실패한 경우 반환할 메시지
	Class<?>[] groups() default { };
	Class<? extends Payload>[] payload() default { };
}
