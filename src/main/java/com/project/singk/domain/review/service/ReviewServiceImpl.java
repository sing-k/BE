package com.project.singk.domain.review.service;

import com.project.singk.domain.activity.domain.ActivityHistory;
import com.project.singk.domain.activity.domain.ActivityType;
import com.project.singk.domain.activity.service.port.ActivityHistoryRepository;
import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.album.service.port.AlbumRepository;
import com.project.singk.domain.common.service.port.S3Repository;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.domain.MemberStatistics;
import com.project.singk.domain.member.service.port.MemberRepository;
import com.project.singk.domain.review.controller.port.ReviewService;
import com.project.singk.domain.review.controller.response.AlbumReviewResponse;
import com.project.singk.domain.review.controller.response.AlbumReviewStatisticsResponse;
import com.project.singk.domain.review.controller.response.MyAlbumReviewResponse;
import com.project.singk.domain.review.domain.AlbumReview;
import com.project.singk.domain.review.domain.AlbumReviewStatistics;
import com.project.singk.domain.review.service.port.AlbumReviewRepository;
import com.project.singk.domain.vote.service.port.AlbumReviewVoteRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import com.project.singk.global.api.OffsetPageResponse;
import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.singk.domain.review.domain.AlbumReviewCreate;
import com.project.singk.global.domain.PkResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@Builder
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final S3Repository s3Repository;
	private final AlbumReviewRepository albumReviewRepository;
	private final MemberRepository memberRepository;
	private final AlbumRepository albumRepository;
    private final AlbumReviewVoteRepository albumReviewVoteRepository;
    private final ActivityHistoryRepository activityHistoryRepository;

    @Override
	public PkResponseDto createAlbumReview(Long memberId, String albumId, AlbumReviewCreate albumReviewCreate) {
		Member member = memberRepository.getById(memberId);
		Album album = albumRepository.getById(albumId);

        if (albumReviewRepository.existsByMemberAndAlbum(member, album)) {
            throw new ApiException(AppHttpStatus.DUPLICATE_ALBUM_REVIEW);
        }

        AlbumReview albumReview = AlbumReview.from(albumReviewCreate, member, album);
		albumReview = albumReviewRepository.save(albumReview);

        // 앨범 평점 수정
        AlbumReviewStatistics reviewStatistics = album.getStatistics();
        reviewStatistics = reviewStatistics.update(member, albumReview, false);
        album = album.updateStatistic(reviewStatistics);
        album = albumRepository.save(album);

        // 활동 점수 부여
        ActivityHistory activity = ActivityHistory.from(ActivityType.WRITE_ALBUM_REVIEW, member);
        activity = activityHistoryRepository.save(activity);

        // 회원 통계 수정
        MemberStatistics memberStatistics = member.getStatistics();
        memberStatistics = memberStatistics.updateActivity(activity);
        memberStatistics = memberStatistics.updateReview(albumReview, false);

        member = member.updateStatistic(memberStatistics);
        member = memberRepository.save(member);

		return PkResponseDto.of(albumReview.getId());
	}

    @Override
    public void deleteAlbumReview(Long memberId, String albumId, Long albumReviewId) {
        AlbumReview albumReview = albumReviewRepository.getById(albumReviewId);
        Member member = memberRepository.getById(memberId);
        Album album = albumRepository.getById(albumId);

        // 감상평 작성자인지 검증
        albumReview.validateReviewer(member);

        // 앨범 평점 수정
        AlbumReviewStatistics statistics = album.getStatistics();
        statistics = statistics.update(member, albumReview, true);

        album = album.updateStatistic(statistics);
        album = albumRepository.save(album);

        // 활동 점수 부여
        ActivityHistory activity = ActivityHistory.from(ActivityType.DELETE_ALBUM_REVIEW, member);
        activity = activityHistoryRepository.save(activity);

        // 회원 통계 수정
        MemberStatistics memberStatistics = member.getStatistics();
        memberStatistics = memberStatistics.updateActivity(activity);
        memberStatistics = memberStatistics.updateReview(albumReview, true);

        member = member.updateStatistic(memberStatistics);
        member = memberRepository.save(member);

        albumReviewRepository.delete(albumReview);
    }

    @Override
    @Transactional(readOnly = true)
    public OffsetPageResponse<AlbumReviewResponse> getAlbumReviews(Long memberId, String albumId, int offset, int limit, String sort) {
        Page<AlbumReview> reviews = albumReviewRepository.getAllByAlbumId(albumId, offset, limit, sort);

        return OffsetPageResponse.of(
                offset,
                limit,
                (int) reviews.getTotalElements(),
                reviews.stream().map(review -> AlbumReviewResponse.from(
                        review,
                        albumReviewVoteRepository.findByMemberIdAndAlbumReviewId(memberId, review.getId()).orElse(null),
                        s3Repository.getPreSignedGetUrl(review.getReviewer().getImageUrl())
                )).toList()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public AlbumReviewStatisticsResponse getAlbumReviewStatistics(String albumId) {
        AlbumReviewStatistics albumReviewStatistics = albumRepository.getAlbumReviewStatisticsByAlbumId(albumId);

        return AlbumReviewStatisticsResponse.from(albumReviewStatistics);
    }

    @Override
    @Transactional(readOnly = true)
    public OffsetPageResponse<MyAlbumReviewResponse> getMyAlbumReview(Long memberId, int offset, int limit, String sort) {
        Page<AlbumReview> reviews = albumReviewRepository.getAllByMemberId(memberId, offset, limit, sort);
        return OffsetPageResponse.of(
                offset,
                limit,
                (int) reviews.getTotalElements(),
                reviews.stream()
                        .map(review -> MyAlbumReviewResponse.from(
                                review,
                                albumReviewVoteRepository.findByMemberIdAndAlbumReviewId(memberId, review.getId()).orElse(null)
                        ))
                        .toList()
        );
    }
}
