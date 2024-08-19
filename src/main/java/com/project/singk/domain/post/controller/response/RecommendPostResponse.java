package com.project.singk.domain.post.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.singk.domain.like.controller.response.LikeResponse;
import com.project.singk.domain.member.controller.response.MemberSimpleResponse;
import com.project.singk.domain.post.domain.RecommendPost;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class RecommendPostResponse {
    private Long id;
    private String title;
    private String content;
    private String recommend;
    private String genre;
    private String link;
    private int comments;
    private LikeResponse like;
    private MemberSimpleResponse writer;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;

    public static RecommendPostResponse from(RecommendPost post, boolean isLike, String link, String profileImgUrl){
        return RecommendPostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .recommend(post.getRecommend().toString())
                .genre(post.getGenre().toString())
                .link(link)
                .like(LikeResponse.from(post.getLikes(), isLike))
                .comments(post.getComments())
                .writer(MemberSimpleResponse.from(post.getMember(), profileImgUrl))
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .build();
    }
}
