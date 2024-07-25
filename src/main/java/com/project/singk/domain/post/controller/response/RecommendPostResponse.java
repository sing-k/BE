package com.project.singk.domain.post.controller.response;

import com.project.singk.domain.post.domain.RecommendPost;
import lombok.Builder;

import java.time.format.DateTimeFormatter;

@Builder
public class RecommendPostResponse {
    private String id;
    private String title;
    private String content;
    private String nickname;
    private String createdAt;
    private String modifiedAt;
    private int likeCount;
    private int commentCount;
    private String thumbnailUrl;
    private String recommendType;

    public static RecommendPostResponse from(RecommendPost post){

        return RecommendPostResponse.builder()
                .id(post.getId().toString())
                .title(post.getTitle())
                .content(post.getContent())
                .nickname(post.getMember().getNickname())
                .createdAt(post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .modifiedAt(post.getModifiedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .thumbnailUrl(post.getThumbnailUrl())
                .recommendType(post.getRecommendType().getName())
                .build();
    }
}
