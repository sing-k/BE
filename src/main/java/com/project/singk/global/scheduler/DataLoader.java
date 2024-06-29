package com.project.singk.global.scheduler;

import com.project.singk.domain.member.domain.Gender;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.domain.MemberStatistics;
import com.project.singk.domain.member.domain.Role;
import com.project.singk.domain.member.service.port.MemberRepository;
import com.project.singk.domain.member.service.port.PasswordEncoderHolder;
import com.project.singk.global.properties.AdminProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final MemberRepository memberRepository;
    private final PasswordEncoderHolder passwordEncoderHolder;
    private final AdminProperties adminProperties;

    @Override
    public void run(ApplicationArguments args) {

        if (memberRepository.existsByEmail(adminProperties.getEmail())) return;

        // Admin 계정 생성
        Member member = memberRepository.save(Member.builder()
                .email(adminProperties.getEmail())
                .password(passwordEncoderHolder.encode(adminProperties.getPassword()))
                .nickname("관리자")
                .role(Role.ROLE_ADMIN)
                .gender(Gender.MALE)
                .name("관리자")
                .birthday(LocalDateTime.of(1999,12,30,0,0,0))
                .statistics(MemberStatistics.empty())
                .build());
    }
}
