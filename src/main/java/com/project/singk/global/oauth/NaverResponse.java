package com.project.singk.global.oauth;

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
public class NaverResponse implements OAuthResponse{

	private Map<String, Object> attributes;

	public static OAuthResponse of (Map<String, Object> attributes) {
		return NaverResponse.builder()
			.attributes((Map<String, Object>) attributes.get(AuthType.NAVER.getAttr()))
			.build();
	}
	@Override
	public String getProvider() {
		return AuthType.NAVER.getId();
	}

	@Override
	public String getProviderId() {
		return attributes.get("id").toString();
	}
}
