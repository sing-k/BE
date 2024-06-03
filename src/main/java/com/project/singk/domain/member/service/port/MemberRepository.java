package com.project.singk.domain.member.service.port;

import java.util.List;
import java.util.Optional;

import com.project.singk.domain.member.domain.Member;

public interface MemberRepository {
	Member save(Member member);
    List<Member> saveAll(List<Member> members);
	Member getById(Long id);
	Member getByEmail(String email);
	Optional<Member> findById(Long id);
	Optional<Member> findByEmail(String email);
	boolean existsByEmail(String email);
	boolean existsByNickname(String nickname);
}
