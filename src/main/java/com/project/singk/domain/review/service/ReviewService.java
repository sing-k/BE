package com.project.singk.domain.review.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.singk.domain.album.infrastructure.entity.AlbumEntity;
import com.project.singk.domain.album.infrastructure.jpa.AlbumJpaRepository;
import com.project.singk.domain.member.infrastructure.MemberEntity;
import com.project.singk.domain.member.infrastructure.MemberJpaRepository;
import com.project.singk.domain.member.service.AuthServiceImpl;
import com.project.singk.domain.review.domain.AlbumReview;
import com.project.singk.domain.review.dto.AlbumReviewRequestDto;
import com.project.singk.domain.review.repository.AlbumReviewRepository;
import com.project.singk.global.domain.PkResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

	private final AuthServiceImpl authServiceImpl;
	private final AlbumReviewRepository albumReviewRepository;
	private final MemberJpaRepository memberJpaRepository;
	private final AlbumJpaRepository albumJpaRepository;


	public PkResponseDto createAlbumReview(String albumId, AlbumReviewRequestDto dto) {
		MemberEntity member = memberJpaRepository.getReferenceById(authServiceImpl.getLoginMemberId());
		AlbumEntity albumEntity = albumJpaRepository.getReferenceById(albumId);

		AlbumReview albumReview = albumReviewRepository.save(dto.toEntity(member, albumEntity));

		return PkResponseDto.of(albumReview.getId());
	}
}
