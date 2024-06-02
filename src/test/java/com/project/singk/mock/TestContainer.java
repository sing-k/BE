package com.project.singk.mock;

import com.project.singk.domain.album.controller.port.AlbumService;
import com.project.singk.domain.album.service.AlbumServiceImpl;
import com.project.singk.domain.album.service.port.*;
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

import com.project.singk.domain.review.controller.port.ReviewService;
import com.project.singk.domain.review.service.ReviewServiceImpl;
import com.project.singk.domain.review.service.port.AlbumReviewRepository;
import com.project.singk.domain.vote.controller.port.VoteService;
import com.project.singk.domain.vote.service.VoteServiceImpl;
import com.project.singk.domain.vote.service.port.AlbumReviewVoteRepository;
import lombok.Builder;

public class TestContainer {
	public final MailSender mailSender;
	public final UUIDHolder uuidHolder;
	public final PasswordEncoderHolder passwordEncoderHolder;
	public final MemberRepository memberRepository;
    public final AlbumRepository albumRepository;
    public final TrackRepository trackRepository;
    public final ArtistRepository artistRepository;
    public final AlbumImageRepository albumImageRepository;
    public final AlbumReviewRepository albumReviewRepository;
    public final AlbumReviewVoteRepository albumReviewVoteRepository;
    public final SpotifyRepository spotifyRepository;
	public final S3Repository s3Repository;
	public final RedisRepository redisRepository;
	public final MailService mailService;
	public final MemberService memberService;
	public final AuthService authService;
    public final AlbumService albumService;
    public final ReviewService reviewService;
    public final VoteService voteService;
	@Builder
	public TestContainer() {
		this.mailSender = new FakeMailSender();
		this.uuidHolder = new FakeUUIDHolder("uuid");
		this.passwordEncoderHolder = new FakePasswordEncoderHolder("encodedPassword");
		this.memberRepository = new FakeMemberRepository();
        this.albumRepository = new FakeAlbumRepository();
        this.trackRepository = new FakeTrackRepository();
        this.artistRepository = new FakeArtistRepository();
        this.albumImageRepository = new FakeAlbumImageRepository();
        this.albumReviewRepository = new FakeAlbumReviewRepository();
        this.albumReviewVoteRepository = new FakeAlbumReviewVoteRepository();
        this.spotifyRepository = new FakeSpotifyRepository();
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
        this.albumService = AlbumServiceImpl.builder()
                .albumRepository(this.albumRepository)
                .albumImageRepository(this.albumImageRepository)
                .spotifyRepository(this.spotifyRepository)
                .trackRepository(this.trackRepository)
                .artistRepository(this.artistRepository)
                .build();
        this.reviewService = ReviewServiceImpl.builder()
                .albumReviewRepository(this.albumReviewRepository)
                .albumRepository(this.albumRepository)
                .memberRepository(this.memberRepository)
                .build();
        this.voteService = VoteServiceImpl.builder()
                .albumReviewVoteRepository(this.albumReviewVoteRepository)
                .memberRepository(this.memberRepository)
                .albumReviewRepository(this.albumReviewRepository)
                .build();
	}

}
