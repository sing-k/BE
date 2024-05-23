package com.project.singk.domain.review.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.album.repository.AlbumRepository;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.repository.MemberRepository;
import com.project.singk.domain.member.service.AuthService;
import com.project.singk.domain.review.domain.AlbumReview;
import com.project.singk.domain.review.dto.AlbumReviewRequestDto;
import com.project.singk.domain.review.repository.AlbumReviewRepository;
import com.project.singk.global.domain.PkResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

	private final AuthService authService;
	private final AlbumReviewRepository albumReviewRepository;
	private final MemberRepository memberRepository;
	private final AlbumRepository albumRepository;


	public PkResponseDto createAlbumReview(String albumId, AlbumReviewRequestDto dto) {
		Member member = memberRepository.getReferenceById(authService.getLoginMemberId());
		Album album = albumRepository.getReferenceById(albumId);

		AlbumReview albumReview = albumReviewRepository.save(dto.toEntity(member, album));

		return PkResponseDto.of(albumReview.getId());
	}
}
