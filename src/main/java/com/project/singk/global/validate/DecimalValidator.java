package com.project.singk.global.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DecimalValidator implements ConstraintValidator<ValidDecimal, String> {
    private static final String DECIMAL_PATTERN = "^\\d+\\.\\d+$";
    private double min;
    private double max;

    @Override
    public void initialize(ValidDecimal constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        if (!value.matches(DECIMAL_PATTERN)) {
            return false;
        }

        try {
            double numericValue = Double.parseDouble(value);
            return numericValue >= min && numericValue <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
