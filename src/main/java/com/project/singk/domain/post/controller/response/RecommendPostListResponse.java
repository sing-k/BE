package com.project.singk.domain.post.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.singk.domain.member.controller.response.MemberResponse;
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
    private String content;
    private String link;
    private String recommend;
    private String genre;
    private int likes;
    private int comments;
    private MemberResponse writer;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    public static RecommendPostListResponse from(RecommendPost post, String link, String profileImgUrl) {
        return RecommendPostListResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .recommend(post.getRecommend().getName())
                .genre(post.getGenre().getName())
                .link(link)
                .likes(post.getLikes())
                .comments(post.getComments())
                .writer(MemberResponse.from(post.getMember(), profileImgUrl))
                .createdAt(post.getCreatedAt())
                .build();
    }
}
