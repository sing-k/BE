package com.project.singk.global.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.springframework.web.multipart.MultipartFile;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ImageValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Image {
	String message() default "Image MIME 유효성 검사에 실패했습니다. e.g. image/png, image/jpeg";
	Class<?>[] groups() default { };
	Class<? extends Payload>[] payload() default { };
}
