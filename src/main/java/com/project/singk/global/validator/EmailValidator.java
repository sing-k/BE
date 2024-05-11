package com.project.singk.global.validator;

import org.springframework.util.StringUtils;

import com.project.singk.domain.mail.domain.MailDomain;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<Email, String> {
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if (!StringUtils.hasText(value)) {
			return false;
		}

		for (MailDomain mail : MailDomain.values()) {
			if (mail.equals(value)) return true;
		}

		return false;
	}
}
