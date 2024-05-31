package com.project.singk.domain.vote.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.singk.domain.member.infrastructure.MemberEntity;
import com.project.singk.domain.review.domain.AlbumReview;
import com.project.singk.domain.vote.domain.AlbumReviewVote;

public interface AlbumReviewVoteRepository extends JpaRepository<AlbumReviewVote, Long> {
	boolean existsByMemberAndAlbumReview(MemberEntity member, AlbumReview albumReview);
	Optional<AlbumReviewVote> findByMemberAndAlbumReview(MemberEntity member, AlbumReview albumReview);
}
