package com.project.singk.domain.post.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.singk.domain.like.controller.response.LikeResponse;
import com.project.singk.domain.member.controller.response.MemberResponse;
import com.project.singk.domain.member.controller.response.MemberSimpleResponse;
import com.project.singk.domain.post.domain.RecommendPost;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class RecommendPostListResponse {
    private Long id;
    private String title;
    private String link;
    private String recommend;
    private String genre;
    private LikeResponse like;
    private int comments;
    private MemberSimpleResponse writer;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;

    public static RecommendPostListResponse from(RecommendPost post, boolean isLike, String link, String profileImgUrl) {
        return RecommendPostListResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .recommend(post.getRecommend().getName())
                .genre(post.getGenre().getName())
                .link(link)
                .like(LikeResponse.from(post.getLikes(), isLike))
                .comments(post.getComments())
                .writer(MemberSimpleResponse.from(post.getMember(), profileImgUrl))
                .createdAt(post.getCreatedAt())
                .build();
    }
}
