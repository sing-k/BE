package com.project.singk.domain.member.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.project.singk.domain.member.domain.AuthType;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.domain.SingKOAuth2User;
import com.project.singk.domain.member.dto.oauth.GoogleResponse;
import com.project.singk.domain.member.dto.oauth.KakaoResponse;
import com.project.singk.domain.member.dto.oauth.NaverResponse;
import com.project.singk.domain.member.dto.oauth.OAuthResponse;
import com.project.singk.domain.member.repository.MemberRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SingKOAuth2UserService extends DefaultOAuth2UserService {

	private final MemberRepository memberRepository;
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);
		System.out.println(oAuth2User);

		String registrationId = userRequest.getClientRegistration().getRegistrationId();

		// TODO : 리팩토링
		OAuthResponse oAuthResponse = null;
		if (AuthType.GOOGLE.getId().equals(registrationId)) {
			oAuthResponse = GoogleResponse.of(oAuth2User.getAttributes());
		} else if (AuthType.NAVER.getId().equals(registrationId)) {
			oAuthResponse = NaverResponse.of(oAuth2User.getAttributes());
		} else if (AuthType.KAKAO.getId().equals(registrationId)) {
			oAuthResponse = KakaoResponse.of(oAuth2User.getAttributes());
		} else {
			// 문제 가능성 있음
			throw new ApiException(AppHttpStatus.INVALID_OAUTH_TYPE);
		}

		Member member = memberRepository.findByEmail(oAuthResponse.getEmail()).orElse(null);
		if (member != null) {
			return SingKOAuth2User.of(oAuthResponse, false);
		}

		return SingKOAuth2User.of(oAuthResponse, true);
	}
}
