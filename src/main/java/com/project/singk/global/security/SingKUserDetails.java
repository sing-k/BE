package com.project.singk.global.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.domain.Role;
import com.project.singk.domain.member.infrastructure.MemberEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SingKUserDetails extends MemberEntity implements UserDetails {

	private Long id;
	private String email;
	private String password;
	private Role role;

	private SingKUserDetails(Member member) {
		this.id = member.getId();
		this.email = member.getEmail();
		this.password = member.getPassword();
		this.role = member.getRole();
	}

	private SingKUserDetails(Long id, String email, Role role) {
		this.id = id;
		this.email = email;
		this.role = role;
	}

	public static SingKUserDetails of(Long id, String email, String role) {
		return new SingKUserDetails(id, email, Role.valueOf(role));
	}

	public static SingKUserDetails of(Member member) {
		return new SingKUserDetails(member);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
