package com.project.singk.domain.vote.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.repository.MemberRepository;
import com.project.singk.domain.member.service.AuthService;
import com.project.singk.domain.review.domain.AlbumReview;
import com.project.singk.domain.review.repository.AlbumReviewRepository;
import com.project.singk.domain.vote.domain.AlbumReviewVote;
import com.project.singk.domain.vote.dto.VoteRequestDto;
import com.project.singk.domain.vote.repository.AlbumReviewVoteRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import com.project.singk.global.domain.PkResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class VoteService {

	private final AuthService authService;
	private final AlbumReviewVoteRepository albumReviewVoteRepository;
	private final MemberRepository memberRepository;
	private final AlbumReviewRepository albumReviewRepository;

	public PkResponseDto createAlbumReviewVote(Long albumReviewId, VoteRequestDto dto) {
		Member member = memberRepository.getReferenceById(authService.getLoginMemberId());
		AlbumReview albumReview = albumReviewRepository.findById(albumReviewId)
			.orElseThrow(() -> new ApiException(AppHttpStatus.NOT_FOUND_ALBUM_REVIEW));

		// 자신의 리뷰에는 공감/비공감 불가
		if (albumReview.getMember().equals(member)) {
			throw new ApiException(AppHttpStatus.INVALID_ALBUM_REVIEW_VOTE);
		}

		// 이미 공감/비공감 했는 지 확인
		if (albumReviewVoteRepository.existsByMemberAndAlbumReview(member, albumReview)) {
			throw new ApiException(AppHttpStatus.DUPLICATE_ALBUM_REVIEW_VOTE);
		}

		// 공감/비공감 카운트 증가
		albumReview.increaseVoteCount(dto.toEnum());

		AlbumReviewVote vote = albumReviewVoteRepository.save(AlbumReviewVote.builder()
			.type(dto.toEnum())
			.member(member)
			.albumReview(albumReview)
			.build());

		return PkResponseDto.of(vote.getId());
	}

	public void deleteAlbumReviewVote(Long albumReviewId, VoteRequestDto dto) {
		Member member = memberRepository.getReferenceById(authService.getLoginMemberId());
		AlbumReview albumReview = albumReviewRepository.findById(albumReviewId)
			.orElseThrow(() -> new ApiException(AppHttpStatus.NOT_FOUND_ALBUM_REVIEW));

		// 자신의 리뷰에는 공감/비공감 취소 불가
		if (albumReview.getMember().equals(member)) {
			throw new ApiException(AppHttpStatus.INVALID_ALBUM_REVIEW_VOTE);
		}

		// 공감/비공감 했는 지 확인
		AlbumReviewVote vote = albumReviewVoteRepository.findByMemberAndAlbumReview(member, albumReview)
			.orElseThrow(() -> new ApiException(AppHttpStatus.NOT_FOUND_ALBUM_REVIEW_VOTE));

		// 공감/비공감 카운트 감소
		albumReview.decreaseVoteCount(dto.toEnum());

		albumReviewVoteRepository.delete(vote);
	}
}
