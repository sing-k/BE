package com.project.singk.domain.member.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.dto.MemberRequestDto;
import com.project.singk.domain.member.dto.MemberResponseDto;
import com.project.singk.domain.member.repository.MemberRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import com.project.singk.global.domain.PkResponseDto;
import com.project.singk.global.properties.S3Properties;
import com.project.singk.global.util.S3Util;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

	private final AuthService authService;
	private final MemberRepository memberRepository;
	private final S3Util s3Util;
	private final S3Properties s3Properties;
	private Member findMember(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new ApiException(AppHttpStatus.NOT_FOUND_MEMBER));
	}

	public MemberResponseDto.Detail getMember() {
		Member member = findMember(authService.getLoginMemberId());
		String imageUrl = s3Util.getPreSignedGetUrl(member.getImageUrl());
		return MemberResponseDto.Detail.of(member, imageUrl);
	}

	public PkResponseDto updateNickname(MemberRequestDto dto) {
		if (memberRepository.existsByNickname(dto.getNickname())) {
			throw new ApiException(AppHttpStatus.DUPLICATE_NICKNAME);
		}

		Member member = findMember(authService.getLoginMemberId());
		member.updateNickname(dto.getNickname());
		return PkResponseDto.of(member.getId());
	}

	public PkResponseDto updateProfileImage(MultipartFile image) {
		Member member = findMember(authService.getLoginMemberId());
		String key = s3Properties.getPath().getProfile() + UUID.randomUUID();

		s3Util.uploadFile(key, image);
		member.updateImage(key);

		return PkResponseDto.of(member.getId());
	}

	public void deleteProfileImage() {
		Member member = findMember(authService.getLoginMemberId());

		s3Util.deleteFile(member.getImageUrl());
		member.updateImage(null);
	}
}
