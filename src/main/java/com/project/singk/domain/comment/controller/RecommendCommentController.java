package com.project.singk.domain.comment.controller;

import com.project.singk.domain.comment.controller.port.RecommendCommentService;
import com.project.singk.domain.comment.controller.response.CommentResponse;
import com.project.singk.domain.comment.domain.CommentCreate;
import com.project.singk.domain.member.controller.port.AuthService;
import com.project.singk.global.api.BaseResponse;
import com.project.singk.global.domain.PkResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/recommend")
public class RecommendCommentController {

    private final RecommendCommentService commentService;
    private final AuthService authService;

    @GetMapping("/{postId}/comments")
    public BaseResponse<List<CommentResponse>> getRecommendComments(
            @PathVariable Long postId
    ) {
        return BaseResponse.ok(commentService.getRecommendComments(
                authService.getLoginMemberId(),
                postId
        ));
    }

    @PostMapping(value = {"/{postId}/comments", "/{postId}/comments/{parentId}"})
    public BaseResponse<PkResponseDto> createRecommendComment(
            @RequestBody CommentCreate commentCreate,
            @PathVariable Long postId,
            @PathVariable(required = false) Long parentId
    ) {
        return BaseResponse.created(commentService.createRecommendComment(
                authService.getLoginMemberId(),
                postId,
                parentId,
                commentCreate
        ));
    }

    @PutMapping("/comments/{commentId}")
    public BaseResponse<PkResponseDto> updateRecommendComment(
            @PathVariable Long commentId
            ,@RequestBody CommentCreate commentCreate){
        return BaseResponse.ok(commentService.updateRecommendComment(
                authService.getLoginMemberId(),
                commentId,
                commentCreate
        ));
    }

    @DeleteMapping("/comments/{commentId}")
    public BaseResponse<Void> deleteRecommendComment(@PathVariable Long commentId){
        commentService.deleteRecommendComment(
                authService.getLoginMemberId(),
                commentId
        );
        return BaseResponse.ok();
    }

}