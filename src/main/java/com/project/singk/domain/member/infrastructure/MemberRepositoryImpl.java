package com.project.singk.domain.member.infrastructure;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.service.port.MemberRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {
	private final MemberJpaRepository memberJpaRepository;

	@Override
	public Member save(Member member) {
		return memberJpaRepository.save(MemberEntity.from(member)).toModel();
	}

	@Override
	public Member getById(Long id) {
		return findById(id)
			.orElseThrow(() -> new ApiException(AppHttpStatus.NOT_FOUND_MEMBER));
	}

	@Override
	public Member getByEmail(String email) {
		return findByEmail(email)
			.orElseThrow(() -> new ApiException(AppHttpStatus.NOT_FOUND_MEMBER));
	}

	@Override
	public Optional<Member> findById(Long id) {
		return memberJpaRepository.findById(id).map(MemberEntity::toModel);
	}

	@Override
	public Optional<Member> findByEmail(String email) {
		return memberJpaRepository.findByEmail(email).map(MemberEntity::toModel);
	}

	@Override
	public boolean existsByEmail(String email) {
		return memberJpaRepository.existsByEmail(email);
	}

	@Override
	public boolean existsByNickname(String nickname) {
		return memberJpaRepository.existsByNickname(nickname);
	}
}
