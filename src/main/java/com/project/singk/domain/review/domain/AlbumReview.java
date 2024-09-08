package com.project.singk.domain.review.domain;

import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.member.domain.Member;

import com.project.singk.domain.vote.domain.VoteType;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AlbumReview {
    private final Long id;
    private final String content;
    private final int score;
    private final int prosCount;
    private final int consCount;
    private final LocalDateTime createdAt;
    private final Member reviewer;
    private final Album album;

    @Builder
    public AlbumReview(Long id, String content, int score, int prosCount, int consCount, LocalDateTime createdAt, Member reviewer, Album album) {
        this.id = id;
        this.content = content;
        this.score = score;
        this.prosCount = prosCount;
        this.consCount = consCount;
        this.createdAt = createdAt;
        this.reviewer = reviewer;
        this.album = album;
    }

    public static AlbumReview from(
            AlbumReviewCreate albumReviewCreate,
            Member writer,
            Album album
    ) {
        return AlbumReview.builder()
                .content(albumReviewCreate.getContent())
                .score(albumReviewCreate.getScore())
                .prosCount(0)
                .consCount(0)
                .reviewer(writer)
                .album(album)
                .build();
    }

    public void validateVoter(Member voter) {
        if (this.reviewer.getId().equals(voter.getId())) {
            throw new ApiException(AppHttpStatus.INVALID_ALBUM_REVIEW_VOTER);
        }
    }

    public void validateReviewer(Member reviewer) {
        if (!this.reviewer.getId().equals(reviewer.getId())) {
            throw new ApiException(AppHttpStatus.FORBIDDEN_ALBUM_REVIEW);
        }
    }

    public AlbumReview vote(VoteType type) {
        return AlbumReview.builder()
                .id(this.id)
                .content(this.content)
                .score(this.score)
                .prosCount(VoteType.PROS.equals(type) ? this.prosCount + 1 : this.prosCount)
                .consCount(VoteType.CONS.equals(type) ? this.consCount + 1 : this.consCount)
                .reviewer(this.reviewer)
                .album(this.album)
                .build();
    }

    public AlbumReview withdraw(VoteType type) {
        return AlbumReview.builder()
                .id(this.id)
                .content(this.content)
                .score(this.score)
                .prosCount(VoteType.PROS.equals(type) ? this.prosCount - 1 : this.prosCount)
                .consCount(VoteType.CONS.equals(type) ? this.consCount - 1 : this.consCount)
                .reviewer(this.reviewer)
                .album(this.album)
                .build();
    }


}
