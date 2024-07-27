package com.project.singk.domain.post.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.singk.domain.member.controller.response.MemberResponse;
import com.project.singk.domain.post.domain.RecommendPost;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class RecommendPostResponse {
    private String id;
    private String title;
    private String recommend;
    private String genre;
    private String link;
    private int likes;
    private int comments;
    private MemberResponse writer;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    public static RecommendPostResponse from(RecommendPost post, String link, String profileImgUrl){

        return RecommendPostResponse.builder()
                .id(post.getId().toString())
                .title(post.getTitle())
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
