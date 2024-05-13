package com.project.singk.domain.member.dto.oauth;

import java.util.Map;

import com.project.singk.domain.member.domain.AuthType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KakaoResponse implements OAuthResponse {

	private Map<String, Object> attributes;
	private Map<String, Object> accounts;
	private Map<String, Object> profiles;

	public static OAuthResponse of (Map<String, Object> attributes) {

		Map<String, Object> accounts = (Map<String, Object>) attributes.get(AuthType.KAKAO.getAttr());
		Map<String, Object> profiles = (Map<String, Object>) accounts.get("profile");

		return KakaoResponse.builder()
			.attributes(attributes)
			.accounts(accounts)
			.profiles(profiles)
			.build();
	}
	@Override
	public String getProvider() {
		return AuthType.KAKAO.getId();
	}

	@Override
	public String getProviderId() {
		return attributes.get("id").toString();
	}

	@Override
	public String getEmail() {
		return null;
	}

	@Override
	public String getName() {
		return profiles.get("nickname").toString();
	}
}
