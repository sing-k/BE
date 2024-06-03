package com.project.singk.global.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ImageValidator implements ConstraintValidator<Image, MultipartFile> {

    // TODO : Enum 클래스로 관리 ?
    List<String> contentTypes = List.of("image/png", "image/jpeg");
	@Override
	public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
		if (file == null || file.isEmpty()) {
            return false;
        }

        for (String contentType : contentTypes) {
            if (contentType.equals(file.getContentType())) {
                return true;
            }
        }

        return false;
	}
}
