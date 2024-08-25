package com.project.singk.domain.vote.infrastructure;

import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.infrastructure.MemberEntity;
import com.project.singk.domain.review.domain.AlbumReview;
import com.project.singk.domain.review.infrastructure.AlbumReviewEntity;
import com.project.singk.domain.vote.domain.AlbumReviewVote;
import com.project.singk.domain.vote.service.port.AlbumReviewVoteRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AlbumReviewVoteRepositoryImpl implements AlbumReviewVoteRepository {

    private final AlbumReviewVoteJpaRepository albumReviewVoteJpaRepository;

    @Override
    public AlbumReviewVote save(AlbumReviewVote albumReviewVote) {
        return albumReviewVoteJpaRepository.save(AlbumReviewVoteEntity.from(albumReviewVote)).toModel();
    }

    @Override
    public AlbumReviewVote getById(Long id) {
        return findById(id).orElseThrow(() -> new ApiException(AppHttpStatus.NOT_FOUND_ALBUM_REVIEW_VOTE));
    }

    @Override
    public AlbumReviewVote getByMemberIdAndAlbumReviewId(Long memberId, Long albumReviewId) {
        return findByMemberIdAndAlbumReviewId(memberId, albumReviewId)
                .orElseThrow(() -> new ApiException(AppHttpStatus.NOT_FOUND_ALBUM_REVIEW_VOTE));
    }

    @Override
    public Optional<AlbumReviewVote> findById(Long id) {
        return albumReviewVoteJpaRepository.findById(id).map(AlbumReviewVoteEntity::toModel);
    }

    @Override
    public Optional<AlbumReviewVote> findByMemberIdAndAlbumReviewId(Long memberId, Long albumReviewId) {
        return albumReviewVoteJpaRepository.findByMemberIdAndAlbumReviewId(
                memberId,
                albumReviewId
        ).map(AlbumReviewVoteEntity::toModel);
    }

    @Override
    public boolean existsByMemberAndAlbumReview(Member member, AlbumReview albumReview) {
        return albumReviewVoteJpaRepository.existsByMemberAndAlbumReview(
                MemberEntity.from(member),
                AlbumReviewEntity.from(albumReview)
        );
    }

    @Override
    public void delete(AlbumReviewVote albumReviewVote) {
        albumReviewVoteJpaRepository.delete(AlbumReviewVoteEntity.from(albumReviewVote));
    }
}
