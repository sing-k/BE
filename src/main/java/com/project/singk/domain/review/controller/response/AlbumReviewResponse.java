package com.project.singk.domain.review.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.singk.domain.member.controller.response.MemberResponse;
import com.project.singk.domain.review.domain.AlbumReview;
import com.project.singk.domain.vote.controller.response.VoteResponse;
import com.project.singk.domain.vote.domain.AlbumReviewVote;
import com.project.singk.domain.vote.domain.VoteType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class AlbumReviewResponse {
    private Long id;
    private String content;
    private int score;
    private VoteResponse vote;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    private MemberResponse reviewer;

    public static AlbumReviewResponse from(AlbumReview albumReview, AlbumReviewVote albumReviewVote, String imageUrl) {
       return AlbumReviewResponse.builder()
               .id(albumReview.getId())
               .content(albumReview.getContent())
               .score(albumReview.getScore())
               .vote(VoteResponse.from(
                       albumReview.getProsCount(),
                       albumReview.getConsCount(),
                       albumReviewVote == null ? VoteType.NONE : albumReviewVote.getType()
               ))
               .createdAt(albumReview.getCreatedAt())
               .reviewer(MemberResponse.from(albumReview.getReviewer(), imageUrl))
               .build();
    }

}
