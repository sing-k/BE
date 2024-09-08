package com.project.singk.domain.vote.domain;

import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.review.domain.AlbumReview;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AlbumReviewVote {
    private final Long id;
    private final VoteType type;
    private final Member member;
    private final AlbumReview albumReview;

    @Builder
    public AlbumReviewVote(Long id, VoteType type, Member member, AlbumReview albumReview) {
        this.id = id;
        this.type = type;
        this.member = member;
        this.albumReview = albumReview;
    }

    public static AlbumReviewVote from (VoteCreate voteCreate, Member member, AlbumReview albumReview) {
        return AlbumReviewVote.builder()
                .type(VoteType.valueOf(voteCreate.getType()))
                .member(member)
                .albumReview(albumReview)
                .build();
    }

    public void validateType(VoteType type) {
        if (!this.type.equals(type)) {
            throw new ApiException(AppHttpStatus.NOT_MATCH_ALBUM_REVIEW_VOTE_TYPE);
        }
    }
}
