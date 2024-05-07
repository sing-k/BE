package com.project.singk.global.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.singk.domain.member.repository.MemberRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SingKUserDetailsService implements UserDetailsService {
	private final MemberRepository memberRepository;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return memberRepository.findByEmail(email)
			.map(SingKUserDetails::of)
			.orElseThrow(() -> new ApiException(AppHttpStatus.NOT_FOUND_MEMBER));
	}
}
