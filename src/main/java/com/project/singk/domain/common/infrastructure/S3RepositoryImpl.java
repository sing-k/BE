package com.project.singk.domain.common.infrastructure;

import java.io.IOException;
import java.time.Duration;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.project.singk.domain.common.service.port.S3Repository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import com.project.singk.global.properties.S3Properties;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

@Service
@RequiredArgsConstructor
public class S3RepositoryImpl implements S3Repository {
	private final S3Client client;
	private final S3Presigner presigner;
	private final S3Properties s3Properties;

	@Override
	public void putObject(String key, MultipartFile file) {

		PutObjectRequest request = PutObjectRequest.builder()
			.bucket(s3Properties.getBucket())
			.key(key)
			.contentType(file.getContentType())
			.contentLength(file.getSize())
			.build();

		try {
			PutObjectResponse response = client.putObject(
				request,
				RequestBody.fromInputStream(
					file.getInputStream(),
					file.getSize()
				)
			);
		} catch (S3Exception e) {
			throw new ApiException(AppHttpStatus.FAILED_REQUEST_S3);
		} catch (IOException e) {
			throw new ApiException(AppHttpStatus.FAILED_IO);
		}
	}

	@Override
	public String getPreSignedGetUrl(String key) {

		if(!StringUtils.hasText(key)) {
			return null;
		}

		GetObjectRequest getRequest = GetObjectRequest.builder()
			.bucket(s3Properties.getBucket())
			.key(key)
			.build();

		GetObjectPresignRequest preSignedGetUrlRequest = GetObjectPresignRequest.builder()
			.signatureDuration(Duration.ofMillis(s3Properties.getExpirationMillis()))
			.getObjectRequest(getRequest)
			.build();

		try {
			return presigner.presignGetObject(preSignedGetUrlRequest).url().toString();
		} catch (S3Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void deleteObject(String key) {
		DeleteObjectRequest request = DeleteObjectRequest.builder()
			.bucket(s3Properties.getBucket())
			.key(key)
			.build();

		DeleteObjectResponse response = client.deleteObject(request);
	}

}
