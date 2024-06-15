package com.project.singk.global.validate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateValidator implements ConstraintValidator<Date, String> {
	private static final String DATE_PATTERN = "yyyy-MM-dd";
	private static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	@Override
	public boolean isValid(String date, ConstraintValidatorContext context) {
		if (date == null) {
			return true;
		}
		return isValidFormat(date, DATE_PATTERN) || isValidFormat(date, DATETIME_PATTERN);
	}

	private boolean isValidFormat(String date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setLenient(false); // lenient 모드를 false로 설정하여 유효하지 않은 날짜를 허용하지 않음
		try {
			sdf.parse(date); // 유효하지 않은 날짜 형식일 경우 ParseException이 발생함
		} catch (ParseException e) {
			return false; // 유효하지 않은 날짜 형식
		}
		return true; // 유효한 날짜 형식
	}
}
