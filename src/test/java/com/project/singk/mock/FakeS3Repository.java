package com.project.singk.mock;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.project.singk.domain.common.service.port.S3Repository;

public class FakeS3Repository implements S3Repository {
	private final Map<String, MultipartFile> data = Collections.synchronizedMap(new HashMap<>());
	@Override
	public void putObject(String key, MultipartFile file) {
		if (StringUtils.hasText(key)) {
			data.put(key, file);
		}
	}

	@Override
	public String getPreSignedGetUrl(String key) {
		if (data.containsKey(key)) {
			return "imageUrl";
		}
		return null;
	}

	@Override
	public void deleteObject(String key) {
		data.remove(key);
	}
}
