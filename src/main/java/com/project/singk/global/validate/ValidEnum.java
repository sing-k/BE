package com.project.singk.global.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = EnumValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEnum {
	String message() default "Enum 유효성 검사에 실패했습니다.";
	Class<? extends Enum<?>> enumClass();
	Class<?>[] groups() default { };
	Class<? extends Payload>[] payload() default { };
	boolean ignoreCase() default true;
}
