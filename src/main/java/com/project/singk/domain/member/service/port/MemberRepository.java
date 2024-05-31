package com.project.singk.domain.member.service.port;

import java.util.Optional;

import com.project.singk.domain.member.domain.Member;

public interface MemberRepository {
	Member save(Member member);
	Member getById(Long id);
	Member getByEmail(String email);
	Optional<Member> findById(Long id);
	Optional<Member> findByEmail(String email);
	boolean existsByEmail(String email);
	boolean existsByNickname(String nickname);
}
