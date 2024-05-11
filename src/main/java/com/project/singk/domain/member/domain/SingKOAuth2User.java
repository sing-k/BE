package com.project.singk.domain.member.domain;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.project.singk.domain.member.dto.oauth.OAuthResponse;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SingKOAuth2User extends Member implements OAuth2User {
	private String email;
	private String name;
	private Role role;
	private boolean isNewbie;

	private SingKOAuth2User(String email, String name, Role role, boolean isNewbie) {
		this.email = email;
		this.name = name;
		this.role = role;
		this.isNewbie = isNewbie;
	}
	public static SingKOAuth2User of(OAuthResponse response,boolean isNewbie) {
		return new SingKOAuth2User(
			response.getEmail(),
			response.getName(),
			Role.ROLE_USER,
			isNewbie
		);
	}

	@Override
	public Map<String, Object> getAttributes() {
		return null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getName() {
		return this.name;
	}

}
