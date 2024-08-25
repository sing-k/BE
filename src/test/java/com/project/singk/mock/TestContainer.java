package com.project.singk.mock;

import com.project.singk.domain.activity.controller.port.ActivityHistoryService;
import com.project.singk.domain.activity.domain.ActivityHistory;
import com.project.singk.domain.activity.service.ActivityHistoryServiceImpl;
import com.project.singk.domain.activity.service.port.ActivityHistoryRepository;
import com.project.singk.domain.album.controller.port.AlbumService;
import com.project.singk.domain.album.service.AlbumServiceImpl;
import com.project.singk.domain.album.service.port.*;
import com.project.singk.domain.comment.controller.port.FreeCommentService;
import com.project.singk.domain.comment.controller.port.RecommendCommentService;
import com.project.singk.domain.comment.service.FreeCommentServiceImpl;
import com.project.singk.domain.comment.service.RecommendCommentServiceImpl;
import com.project.singk.domain.comment.service.port.FreeCommentRepository;
import com.project.singk.domain.comment.service.port.RecommendCommentRepository;
import com.project.singk.domain.common.service.port.RedisRepository;
import com.project.singk.domain.common.service.port.S3Repository;
import com.project.singk.domain.common.service.port.UUIDHolder;
import com.project.singk.domain.like.controller.port.FreeLikeService;
import com.project.singk.domain.like.controller.port.RecommendLikeService;
import com.project.singk.domain.like.service.FreeLikeServiceImpl;
import com.project.singk.domain.like.service.RecommendLikeServiceImpl;
import com.project.singk.domain.like.service.port.FreeCommentLikeRepository;
import com.project.singk.domain.like.service.port.FreePostLikeRepository;
import com.project.singk.domain.like.service.port.RecommendCommentLikeRepository;
import com.project.singk.domain.like.service.port.RecommendPostLikeRepository;
import com.project.singk.domain.member.controller.port.AuthService;
import com.project.singk.domain.member.controller.port.MemberService;
import com.project.singk.domain.member.service.AuthServiceImpl;
import com.project.singk.domain.member.service.MailService;
import com.project.singk.domain.member.service.MemberServiceImpl;
import com.project.singk.domain.member.service.port.CodeGenerator;
import com.project.singk.domain.member.service.port.MailSender;
import com.project.singk.domain.member.service.port.MemberRepository;
import com.project.singk.domain.member.service.port.PasswordEncoderHolder;

import com.project.singk.domain.post.controller.port.FreePostService;
import com.project.singk.domain.post.controller.port.RecommendPostService;
import com.project.singk.domain.post.service.FreePostServiceImpl;
import com.project.singk.domain.post.service.RecommendPostServiceImpl;
import com.project.singk.domain.post.service.port.FreePostRepository;
import com.project.singk.domain.post.service.port.RecommendPostRepository;
import com.project.singk.domain.review.controller.port.ReviewService;
import com.project.singk.domain.review.service.ReviewServiceImpl;
import com.project.singk.domain.review.service.port.AlbumReviewRepository;
import com.project.singk.domain.vote.controller.port.VoteService;
import com.project.singk.domain.vote.service.VoteServiceImpl;
import com.project.singk.domain.vote.service.port.AlbumReviewVoteRepository;
import com.project.singk.global.properties.S3Properties;
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
    public final ActivityHistoryRepository activityHistoryRepository;
    public final FreePostRepository freePostRepository;
    public final FreePostLikeRepository freePostLikeRepository;
    public final FreeCommentRepository freeCommentRepository;
    public final FreeCommentLikeRepository freeCommentLikeRepository;
    public final RecommendPostRepository recommendPostRepository;
    public final RecommendPostLikeRepository recommendPostLikeRepository;
    public final RecommendCommentRepository recommendCommentRepository;
    public final RecommendCommentLikeRepository recommendCommentLikeRepository;
    public final SpotifyRepository spotifyRepository;
	public final S3Repository s3Repository;
	public final RedisRepository redisRepository;
	public final MailService mailService;
	public final MemberService memberService;
	public final AuthService authService;
    public final AlbumService albumService;
    public final ReviewService reviewService;
    public final VoteService voteService;
    public final ActivityHistoryService activityHistoryService;
    public final FreePostService freePostService;
    public final FreeCommentService freeCommentService;
    public final FreeLikeService freeLikeService;
    public final RecommendPostService recommendPostService;
    public final RecommendCommentService recommendCommentService;
    public final RecommendLikeService recommendLikeService;
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
        this.activityHistoryRepository = new FakeActivityHistoryRepository();
        this.freePostRepository = new FakeFreePostRepository();
        this.freePostLikeRepository = new FakeFreePostLikeRepository();
        this.freeCommentRepository = new FakeFreeCommentRepository();
        this.freeCommentLikeRepository = new FakeFreeCommentLikeRepository();
        this.recommendPostRepository = new FakeRecommendPostRepository();
        this.recommendPostLikeRepository = new FakeRecommendPostLikeRepository();
        this.recommendCommentRepository = new FakeRecommendCommentRepository();
        this.recommendCommentLikeRepository = new FakeRecommendCommentLikeRepository();
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
                .albumReviewVoteRepository(this.albumReviewVoteRepository)
                .albumRepository(this.albumRepository)
                .activityHistoryRepository(this.activityHistoryRepository)
                .memberRepository(this.memberRepository)
                .s3Repository(this.s3Repository)
                .build();
        this.voteService = VoteServiceImpl.builder()
                .albumReviewVoteRepository(this.albumReviewVoteRepository)
                .memberRepository(this.memberRepository)
                .albumReviewRepository(this.albumReviewRepository)
                .activityHistoryRepository(this.activityHistoryRepository)
                .build();
        this.activityHistoryService = ActivityHistoryServiceImpl.builder()
                .activityHistoryRepository(this.activityHistoryRepository)
                .build();
        this.freeLikeService = FreeLikeServiceImpl.builder()
                .memberRepository(this.memberRepository)
                .freePostRepository(this.freePostRepository)
                .freePostLikeRepository(this.freePostLikeRepository)
                .freeCommentRepository(this.freeCommentRepository)
                .freeCommentLikeRepository(this.freeCommentLikeRepository)
                .activityHistoryRepository(this.activityHistoryRepository)
                .build();
        this.freePostService = FreePostServiceImpl.builder()
                .memberRepository(this.memberRepository)
                .s3Repository(this.s3Repository)
                .freePostRepository(this.freePostRepository)
                .freeLikeService(this.freeLikeService)
                .activityHistoryRepository(this.activityHistoryRepository)
                .build();
        this.freeCommentService = FreeCommentServiceImpl.builder()
                .memberRepository(this.memberRepository)
                .s3Repository(this.s3Repository)
                .freePostRepository(this.freePostRepository)
                .freeCommentRepository(this.freeCommentRepository)
                .freeLikeService(this.freeLikeService)
                .activityHistoryRepository(this.activityHistoryRepository)
                .build();
        this.recommendLikeService = RecommendLikeServiceImpl.builder()
                .memberRepository(this.memberRepository)
                .recommendPostRepository(this.recommendPostRepository)
                .recommendPostLikeRepository(this.recommendPostLikeRepository)
                .recommendCommentRepository(this.recommendCommentRepository)
                .recommendCommentLikeRepository(this.recommendCommentLikeRepository)
                .activityHistoryRepository(this.activityHistoryRepository)
                .build();
        this.recommendPostService = RecommendPostServiceImpl.builder()
                .memberRepository(this.memberRepository)
                .s3Repository(this.s3Repository)
                .recommendPostRepository(this.recommendPostRepository)
                .uuidHolder(this.uuidHolder)
                .albumImageRepository(this.albumImageRepository)
                .recommendLikeService(this.recommendLikeService)
                .activityHistoryRepository(this.activityHistoryRepository)
                .build();
        this.recommendCommentService = RecommendCommentServiceImpl.builder()
                .memberRepository(this.memberRepository)
                .s3Repository(this.s3Repository)
                .recommendPostRepository(this.recommendPostRepository)
                .recommendCommentRepository(this.recommendCommentRepository)
                .recommendLikeService(this.recommendLikeService)
                .activityHistoryRepository(this.activityHistoryRepository)
                .build();
	}

}
