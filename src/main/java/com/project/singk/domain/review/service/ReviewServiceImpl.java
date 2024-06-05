package com.project.singk.domain.review.service;

import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.album.service.port.AlbumRepository;
import com.project.singk.domain.member.controller.port.AuthService;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.service.port.MemberRepository;
import com.project.singk.domain.review.controller.port.ReviewService;
import com.project.singk.domain.review.controller.response.AlbumReviewStatisticsResponse;
import com.project.singk.domain.review.domain.AlbumReview;
import com.project.singk.domain.review.domain.AlbumReviewStatistics;
import com.project.singk.domain.review.service.port.AlbumReviewRepository;
import lombok.Builder;
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

	private final AlbumReviewRepository albumReviewRepository;
	private final MemberRepository memberRepository;
	private final AlbumRepository albumRepository;

    @Override
	public PkResponseDto createAlbumReview(Long memberId, String albumId, AlbumReviewCreate albumReviewCreate) {
		Member member = memberRepository.getById(memberId);
		Album album = albumRepository.getById(albumId);

        AlbumReview albumReview = AlbumReview.from(albumReviewCreate, member, album);
		albumReview = albumReviewRepository.save(albumReview);

		return PkResponseDto.of(albumReview.getId());
	}

    @Override
    public AlbumReviewStatisticsResponse getAlbumReviewStatistics(String albumId) {
        AlbumReviewStatistics albumReviewStatistics = albumReviewRepository.getAlbumReviewStatisticsByAlbumId(albumId);

        return AlbumReviewStatisticsResponse.from(albumReviewStatistics);
    }
}
