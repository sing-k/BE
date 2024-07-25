package com.project.singk.domain.post.controller.response;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostListResponse {
    private String id;
    private String title;
    private String createdAt;
    private String updatedAt;
    private int likeCount;
    private int commentCount;
    private String nickname;
    private String imgUrl;
}
