package com.project.singk.domain.comment.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.singk.domain.comment.domain.CommentType;
import com.project.singk.domain.comment.domain.CommentSimplified;
import com.project.singk.domain.like.controller.response.LikeResponse;
import com.project.singk.domain.member.controller.response.MemberSimpleResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@ToString
public class CommentResponse {
    private Long id;
    private Long parentId;
    private Long postId;
    private String type;
    private String content;
    private LikeResponse like;
    private MemberSimpleResponse writer;
    private List<CommentResponse> children;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;

    public static CommentResponse recommendType (CommentSimplified comment, boolean isLike, String profileImgUrl) {
        return CommentResponse.builder()
                .id(comment.getId())
                .parentId(comment.getParentId())
                .postId(comment.getPostId())
                .type(CommentType.RECOMMEND.getName())
                .content(comment.getContent())
                .like(LikeResponse.from(comment.getLikes(), isLike))
                .writer(MemberSimpleResponse.from(comment.getMember(), profileImgUrl))
                .children(new ArrayList<>())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }

    public static CommentResponse freeType (CommentSimplified comment, boolean isLike, String profileImgUrl) {
        return CommentResponse.builder()
                .id(comment.getId())
                .parentId(comment.getParentId())
                .postId(comment.getPostId())
                .type(CommentType.FREE.getName())
                .content(comment.getContent())
                .like(LikeResponse.from(comment.getLikes(), isLike))
                .writer(MemberSimpleResponse.from(comment.getMember(), profileImgUrl))
                .children(new ArrayList<>())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }

}
