package com.project.singk.domain.review.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.singk.domain.member.controller.response.MemberResponse;
import com.project.singk.domain.review.domain.AlbumReview;
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
    private int pros;
    private int cons;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    private MemberResponse reviewer;

    public static AlbumReviewResponse from(AlbumReview albumReview, String imageUrl) {
       return AlbumReviewResponse.builder()
               .id(albumReview.getId())
               .content(albumReview.getContent())
               .score(albumReview.getScore())
               .pros(albumReview.getProsCount())
               .cons(albumReview.getConsCount())
               .createdAt(albumReview.getCreatedAt())
               .reviewer(MemberResponse.from(albumReview.getReviewer(), imageUrl))
               .build();
    }

}
