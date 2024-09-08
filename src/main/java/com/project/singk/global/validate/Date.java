package com.project.singk.global.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateValidator.class)
public @interface Date {

	String message() default "날짜 포맷 유효성 검사에 실패했습니다.";
	Class<?>[] groups() default { };
	Class<? extends Payload>[] payload() default { };
}
