package com.project.singk.domain.vote.infrastructure;

import java.util.Optional;

import com.project.singk.domain.review.infrastructure.AlbumReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.singk.domain.member.infrastructure.MemberEntity;

public interface AlbumReviewVoteJpaRepository extends JpaRepository<AlbumReviewVoteEntity, Long> {
	boolean existsByMemberAndAlbumReview(MemberEntity member, AlbumReviewEntity albumReviewEntity);
	Optional<AlbumReviewVoteEntity> findByMemberAndAlbumReview(MemberEntity member, AlbumReviewEntity albumReviewEntity);
}
