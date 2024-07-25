package com.project.singk.domain.post.controller.response;

import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.post.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Builder
@ToString
public class PostResponse {
    private String id;
    private String title;
    private String content;
    private String nickname;
    private String createdAt;
    private String modifiedAt;
    private int likeCount;
    private int commentCount;

    public static PostResponse from(Post post){
        return PostResponse.builder()
                .id(post.getId().toString())
                .title(post.getTitle())
                .content(post.getContent())
                .nickname(post.getMember().getNickname())
                .createdAt(post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .modifiedAt(post.getModifiedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .build();
    }
}
