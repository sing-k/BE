package com.project.singk.mock;

import com.project.singk.domain.common.service.port.RedisRepository;
import com.project.singk.domain.common.service.port.S3Repository;
import com.project.singk.domain.common.service.port.UUIDHolder;
import com.project.singk.domain.member.controller.port.AuthService;
import com.project.singk.domain.member.controller.port.MemberService;
import com.project.singk.domain.member.service.AuthServiceImpl;
import com.project.singk.domain.member.service.MailService;
import com.project.singk.domain.member.service.MemberServiceImpl;
import com.project.singk.domain.member.service.port.CodeGenerator;
import com.project.singk.domain.member.service.port.MailSender;
import com.project.singk.domain.member.service.port.MemberRepository;
import com.project.singk.domain.member.service.port.PasswordEncoderHolder;

import lombok.Builder;

public class TestContainer {
	public final MailSender mailSender;
	public final UUIDHolder uuidHolder;
	public final PasswordEncoderHolder passwordEncoderHolder;
	public final MemberRepository memberRepository;
	public final S3Repository s3Repository;
	public final RedisRepository redisRepository;
	public final MailService mailService;
	public final MemberService memberService;
	public final AuthService authService;
	@Builder
	public TestContainer() {
		this.mailSender = new FakeMailSender();
		this.uuidHolder = new FakeUUIDHolder("uuid");
		this.passwordEncoderHolder = new FakePasswordEncoderHolder("encodedPassword");
		this.memberRepository = new FakeMemberRepository();
		this.s3Repository = new FakeS3Repository();
		this.redisRepository = new FakeRedisRepository();
		this.mailService = MailService.builder()
			.mailSender(this.mailSender)
			.build();
		this.authService = AuthServiceImpl.builder()
			.memberRepository(this.memberRepository)
			.passwordEncoderHolder(this.passwordEncoderHolder)
			.redisRepository(this.redisRepository)
			.build();
		this.memberService = MemberServiceImpl.builder()
			.authService(this.authService)
			.memberRepository(this.memberRepository)
			.s3Repository(this.s3Repository)
			.uuidHolder(this.uuidHolder)
			.build();
	}

}
