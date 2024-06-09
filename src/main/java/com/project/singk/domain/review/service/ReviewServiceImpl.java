package com.project.singk.domain.review.service;

import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.album.service.port.AlbumRepository;
import com.project.singk.domain.common.service.port.S3Repository;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.service.port.MemberRepository;
import com.project.singk.domain.review.controller.port.ReviewService;
import com.project.singk.domain.review.controller.request.ReviewSort;
import com.project.singk.domain.review.controller.response.AlbumReviewResponse;
import com.project.singk.domain.review.controller.response.AlbumReviewStatisticsResponse;
import com.project.singk.domain.review.domain.AlbumReview;
import com.project.singk.domain.review.domain.AlbumReviewStatistics;
import com.project.singk.domain.review.service.port.AlbumReviewRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import lombok.Builder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.singk.domain.review.domain.AlbumReviewCreate;
import com.project.singk.global.domain.PkResponseDto;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@Builder
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final S3Repository s3Repository;
	private final AlbumReviewRepository albumReviewRepository;
	private final MemberRepository memberRepository;
	private final AlbumRepository albumRepository;

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
        album = album.increaseReviewScore(albumReviewCreate.getScore());
        album = albumRepository.save(album);

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
        album = album.decreaseReviewScore(albumReview.getScore());
        album = albumRepository.save(album);

        albumReviewRepository.delete(albumReview);
    }

    @Override
    public List<AlbumReviewResponse> getAlbumReviews(String albumId, String sort) {
        List<AlbumReview> reviews = albumReviewRepository.getAllByAlbumId(albumId, ReviewSort.valueOf(sort));

        return reviews.stream().map(review -> AlbumReviewResponse.from(
                review,
                s3Repository.getPreSignedGetUrl(review.getReviewer().getImageUrl())
        )).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AlbumReviewStatisticsResponse getAlbumReviewStatistics(String albumId) {
        AlbumReviewStatistics albumReviewStatistics = albumReviewRepository.getAlbumReviewStatisticsByAlbumId(albumId);

        return AlbumReviewStatisticsResponse.from(albumReviewStatistics);
    }
}
