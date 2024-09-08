package com.project.singk.global.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {

	String message() default "비밀번호 유효성 검사에 실패했습니다.";
	Class<?>[] groups() default { };
	Class<? extends Payload>[] payload() default { };
}
