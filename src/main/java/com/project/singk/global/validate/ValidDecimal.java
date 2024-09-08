package com.project.singk.global.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DecimalValidator.class)
public @interface ValidDecimal {

    double min() default 0.0;
    double max() default Double.MAX_VALUE;
	String message() default "숫자 포맷 유효성 검사에 실패했습니다.";
	Class<?>[] groups() default { };
	Class<? extends Payload>[] payload() default { };
}
