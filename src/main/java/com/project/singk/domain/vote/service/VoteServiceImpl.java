package com.project.singk.domain.vote.service;

import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.service.port.MemberRepository;
import com.project.singk.domain.review.domain.AlbumReview;
import com.project.singk.domain.review.service.port.AlbumReviewRepository;
import com.project.singk.domain.vote.controller.port.VoteService;
import com.project.singk.domain.vote.domain.AlbumReviewVote;
import com.project.singk.domain.vote.domain.VoteType;
import com.project.singk.domain.vote.service.port.AlbumReviewVoteRepository;
import lombok.Builder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.singk.domain.vote.domain.VoteCreate;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import com.project.singk.global.domain.PkResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@Builder
@RequiredArgsConstructor
@Transactional
public class VoteServiceImpl implements VoteService {

	private final AlbumReviewVoteRepository albumReviewVoteRepository;
	private final MemberRepository memberRepository;
	private final AlbumReviewRepository albumReviewRepository;

    @Override
	public PkResponseDto createAlbumReviewVote(Long memberId, Long albumReviewId, VoteCreate voteCreate) {
		Member member = memberRepository.getById(memberId);
		AlbumReview albumReview = albumReviewRepository.getById(albumReviewId);

		// 자신의 리뷰에는 공감/비공감 불가
		albumReview.validateVoter(member);

		// 이미 공감/비공감 했는 지 확인
		if (albumReviewVoteRepository.existsByMemberAndAlbumReview(member, albumReview)) {
			throw new ApiException(AppHttpStatus.DUPLICATE_ALBUM_REVIEW_VOTE);
		}

		// 공감/비공감 카운트 증가
		albumReview = albumReview.vote(VoteType.valueOf(voteCreate.getType()));
        albumReview = albumReviewRepository.save(albumReview);

        AlbumReviewVote albumReviewVote = AlbumReviewVote.from(voteCreate, member, albumReview);
        albumReviewVote = albumReviewVoteRepository.save(albumReviewVote);
		return PkResponseDto.of(albumReviewVote.getId());
	}

    @Override
	public void deleteAlbumReviewVote(Long memberId, Long albumReviewId, VoteCreate voteCreate) {
		Member member = memberRepository.getById(memberId);
		AlbumReview albumReview = albumReviewRepository.getById(albumReviewId);

		// 자신의 리뷰에는 공감/비공감 취소 불가
		albumReview.validateVoter(member);

		// 공감/비공감 했는지 확인
		AlbumReviewVote albumReviewVote = albumReviewVoteRepository.getByMemberAndAlbumReview(member, albumReview);

        // 삭제하려는 투표 유형과 DB에 저장되어 있는 투표 유형이 일치하는지 확인
        albumReviewVote.validateType(VoteType.valueOf(voteCreate.getType()));

		// 공감/비공감 카운트 감소
		albumReview = albumReview.withdraw(VoteType.valueOf(voteCreate.getType()));
        albumReview = albumReviewRepository.save(albumReview);

		albumReviewVoteRepository.delete(albumReviewVote);
	}
}
