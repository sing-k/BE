package com.project.singk.domain.member.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.project.singk.domain.common.service.port.S3Repository;
import com.project.singk.domain.common.service.port.UUIDHolder;
import com.project.singk.domain.member.controller.port.AuthService;
import com.project.singk.domain.member.controller.port.MemberService;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.domain.MemberUpdate;
import com.project.singk.domain.member.controller.response.MyProfileResponse;
import com.project.singk.domain.member.service.port.MemberRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import com.project.singk.global.domain.PkResponseDto;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Service
@Builder
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

	private final AuthService authService;
	private final MemberRepository memberRepository;
	private final S3Repository s3Repository;
	private final UUIDHolder uuidHolder;

	@Transactional(readOnly = true)
	public MyProfileResponse getMyProfile(Long memberId) {
		Member member = memberRepository.getById(memberId);

		String imageUrl = s3Repository.getPreSignedGetUrl(member.getImageUrl());
		return MyProfileResponse.from(member, imageUrl);
	}

	public PkResponseDto update(Long memberId, MemberUpdate memberUpdate) {

		if (memberRepository.existsByNickname(memberUpdate.getNickname())) {
			throw new ApiException(AppHttpStatus.DUPLICATE_NICKNAME);
		}

		Member member = memberRepository.getById(memberId);
		member = member.update(memberUpdate);
		member = memberRepository.save(member);

		return PkResponseDto.of(member.getId());
	}

	public PkResponseDto uploadProfileImage(Long memberId, MultipartFile image) {
		if (image == null || image.isEmpty()) {
			throw new ApiException(AppHttpStatus.INVALID_FILE);
		}

		Member member = memberRepository.getById(memberId);
		String key = "profile-img/" + uuidHolder.randomUUID();

		s3Repository.putObject(key, image);
		member = member.uploadProfileImage(key);
		member = memberRepository.save(member);

		return PkResponseDto.of(member.getId());
	}

	public void deleteProfileImage(Long memberId) {
		Member member = memberRepository.getById(memberId);

		if (member.getImageUrl() == null) {
			throw new ApiException(AppHttpStatus.NOT_FOUND_PROFILE_IMAGE);
		}

		s3Repository.deleteObject(member.getImageUrl());
		member = member.uploadProfileImage(null);

		memberRepository.save(member);
	}
}
