package com.project.singk.global.jwt;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.domain.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SingKUserDetails extends Member implements UserDetails {

	private Long id;
	private String email;
	private String password;
	private Role role;

	public SingKUserDetails(Member member) {
		this.id = member.getId();
		this.email = member.getEmail();
		this.password = member.getPassword();
		this.role = member.getRole();
	}

	public static SingKUserDetails of(String email, String role) {
		return (SingKUserDetails) SingKUserDetails.builder()
			.email(email)
			.role(Role.valueOf(role))
			.build();
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
