package com.project.singk.global.validate;

import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if (!StringUtils.hasText(value)) {
			return false;
		}

		Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
		return pattern.matcher(value).matches();
	}
}
