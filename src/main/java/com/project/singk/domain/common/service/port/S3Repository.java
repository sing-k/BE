package com.project.singk.domain.common.service.port;

import org.springframework.web.multipart.MultipartFile;

public interface S3Repository {
	void putObject(String key, MultipartFile file);
	String getPreSignedGetUrl(String key);
	void deleteObject(String key);

}
