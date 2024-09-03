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
public class GoogleResponse implements OAuthResponse {

	private Map<String, Object> attributes;

	public static OAuthResponse of (Map<String, Object> attributes) {
		return GoogleResponse.builder()
			.attributes(attributes)
			.build();
	}
	@Override
	public String getProvider() {
		return AuthType.GOOGLE.getId();
	}

	@Override
	public String getProviderId() {
		return attributes.get("sub").toString();
	}

}
