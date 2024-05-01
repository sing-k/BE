package com.project.singk.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.singk.domain.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
