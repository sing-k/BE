package com.project.singk.global.oauth;

import java.time.LocalDateTime;

import com.project.singk.domain.member.domain.Gender;

public interface OAuthResponse {
	// 제공자 : Naver, Kakao ...
	String getProvider();
	// 제공자에서 발급해주는 아이디
	String getProviderId();
}
