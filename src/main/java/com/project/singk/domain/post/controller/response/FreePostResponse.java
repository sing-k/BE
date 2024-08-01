package com.project.singk.domain.post.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.singk.domain.like.controller.response.LikeResponse;
import com.project.singk.domain.member.controller.response.MemberSimpleResponse;
import com.project.singk.domain.post.domain.FreePost;
import com.project.singk.domain.post.domain.RecommendPost;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Builder
@ToString
public class FreePostResponse {
    private Long id;
    private String title;
    private String content;
    private LikeResponse like;
    private int comments;
    private MemberSimpleResponse writer;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;

    public static FreePostResponse from(FreePost post, boolean isLike, String profileImgUrl){
        return FreePostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .like(LikeResponse.from(post.getLikes(), isLike))
                .comments(post.getComments())
                .writer(MemberSimpleResponse.from(post.getMember(), profileImgUrl))
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .build();
    }
}
