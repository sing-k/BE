package com.project.singk.domain.member.controller.port;

import org.springframework.web.multipart.MultipartFile;

import com.project.singk.domain.member.controller.response.MyProfileResponse;
import com.project.singk.domain.member.domain.MemberUpdate;
import com.project.singk.global.domain.PkResponseDto;

public interface MemberService {
	MyProfileResponse getMyProfile(Long memberId);
	PkResponseDto update(Long memberId, MemberUpdate memberUpdate);
	PkResponseDto uploadProfileImage(Long memberId, MultipartFile image);
	void deleteProfileImage(Long memberId);
}
