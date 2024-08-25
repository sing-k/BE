package com.project.singk.domain.comment.controller;

import com.project.singk.domain.comment.controller.port.FreeCommentService;
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
@RequestMapping("/api/posts/free/{postId}")
public class FreeCommentController {

    private final FreeCommentService commentService;
    private final AuthService authService;

    @GetMapping("/comments")
    public BaseResponse<List<CommentResponse>> getFreeComments(
            @PathVariable Long postId
    ) {
        return BaseResponse.ok(commentService.getFreeComments(
                authService.getLoginMemberId(),
                postId
        ));
    }

    @GetMapping("/comments/me")
    public BaseResponse<List<CommentResponse>> getMyFreeComments(
            @PathVariable Long postId
    ) {
        return BaseResponse.ok(commentService.getMyFreeComments(
                authService.getLoginMemberId(),
                postId
        ));
    }

    @PostMapping(value = {"/comments", "/comments/{parentId}"})
    public BaseResponse<PkResponseDto> createFreeComment(
            @RequestBody CommentCreate commentCreate,
            @PathVariable Long postId,
            @PathVariable(required = false) Long parentId
    ){
        return BaseResponse.created(commentService.createFreeComment(
                authService.getLoginMemberId(),
                postId,
                parentId,
                commentCreate
        ));
    }
    @PutMapping("/comments/{commentId}")
    public BaseResponse<PkResponseDto> updateFreeComment(
            @PathVariable Long commentId
            ,@RequestBody CommentCreate commentCreate){
        return BaseResponse.ok(commentService.updateFreeComment(
                authService.getLoginMemberId(),
                commentId,
                commentCreate
        ));
    }

    @DeleteMapping("/comments/{commentId}")
    public BaseResponse<Void> deleteFreeComment(
            @PathVariable Long postId,
            @PathVariable Long commentId
    ){
        commentService.deleteFreeComment(
                authService.getLoginMemberId(),
                postId,
                commentId
        );
        return BaseResponse.ok();
    }
}
