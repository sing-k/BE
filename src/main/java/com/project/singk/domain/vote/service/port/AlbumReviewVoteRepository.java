package com.project.singk.domain.vote.service.port;

import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.review.domain.AlbumReview;
import com.project.singk.domain.vote.domain.AlbumReviewVote;

import java.util.Optional;

public interface AlbumReviewVoteRepository {
    AlbumReviewVote save(AlbumReviewVote albumReviewVote);
    AlbumReviewVote getById(Long id);
    AlbumReviewVote getByMemberIdAndAlbumReviewId(Long memberId, Long albumReviewId);
    Optional<AlbumReviewVote> findById(Long id);
    Optional<AlbumReviewVote> findByMemberIdAndAlbumReviewId(Long memberId, Long albumReviewId);
    boolean existsByMemberAndAlbumReview(Member member, AlbumReview albumReview);
    void delete(AlbumReviewVote albumReviewVote);
}
