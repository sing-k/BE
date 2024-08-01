package com.project.singk.domain.post.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.singk.domain.like.controller.response.LikeResponse;
import com.project.singk.domain.member.controller.response.MemberSimpleResponse;
import com.project.singk.domain.post.domain.FreePost;
import com.project.singk.domain.post.domain.RecommendPost;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FreePostListResponse {
    private Long id;
    private String title;
    private LikeResponse like;
    private int comments;
    private MemberSimpleResponse writer;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    public static FreePostListResponse from(FreePost post, boolean isLike, String profileImgUrl) {
        return FreePostListResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .like(LikeResponse.from(post.getLikes(), isLike))
                .comments(post.getComments())
                .writer(MemberSimpleResponse.from(post.getMember(), profileImgUrl))
                .createdAt(post.getCreatedAt())
                .build();
    }
}
