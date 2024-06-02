package com.project.singk.domain.vote.controller.port;

import com.project.singk.domain.vote.domain.VoteCreate;
import com.project.singk.global.domain.PkResponseDto;

public interface VoteService {
    PkResponseDto createAlbumReviewVote(Long memberId, Long albumReviewId, VoteCreate voteCreate);
    void deleteAlbumReviewVote(Long memberId, Long albumReviewId, VoteCreate dto);
}
