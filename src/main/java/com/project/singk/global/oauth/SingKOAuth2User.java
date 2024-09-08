package com.project.singk.global.oauth;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.project.singk.domain.member.domain.Role;
import com.project.singk.domain.member.infrastructure.MemberEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SingKOAuth2User extends MemberEntity implements OAuth2User {
	private Long id;
	private String email;
	private Role role;
	private boolean isNewbie;

	private SingKOAuth2User(String email, Role role, boolean isNewbie) {
		this.email = email;
		this.role = role;
		this.isNewbie = isNewbie;
	}
	private SingKOAuth2User(Long id, String email, Role role, boolean isNewbie) {
		this.id = id;
		this.email = email;
		this.role = role;
		this.isNewbie = isNewbie;
	}
	public static SingKOAuth2User of(OAuthResponse response, boolean isNewbie) {
		return new SingKOAuth2User(
			response.getProviderId(),
			Role.ROLE_USER,
			isNewbie
		);
	}

	public static SingKOAuth2User of(Long id, OAuthResponse response, boolean isNewbie) {
		return new SingKOAuth2User(
			id,
            response.getProviderId(),
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
		return this.email;
	}

}
