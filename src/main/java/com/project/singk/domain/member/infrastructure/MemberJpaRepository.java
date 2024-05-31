package com.project.singk.domain.member.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {
	Optional<MemberEntity> findByEmail(String email);
	Optional<MemberEntity> findByNickname(String nickname);
	boolean existsByEmail(String email);
	boolean existsByNickname(String nickname);
}
