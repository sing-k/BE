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
@RequestMapping("/api/posts/free")
public class FreeCommentController {

    private final FreeCommentService commentService;
    private final AuthService authService;

    @GetMapping("/{postId}/comments")
    public BaseResponse<List<CommentResponse>> getComments(
            @PathVariable Long postId
    ) {
        return BaseResponse.ok(commentService.getFreeComments(
                authService.getLoginMemberId(),
                postId
        ));
    }

    @PostMapping(value = {"/{postId}/comments", "/{postId}/comments/{parentId}"})
    public BaseResponse<PkResponseDto> createComment(
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
    public BaseResponse<PkResponseDto> updateComment(
            @PathVariable Long commentId
            ,@RequestBody CommentCreate commentCreate){
        return BaseResponse.ok(commentService.updateFreeComment(
                authService.getLoginMemberId(),
                commentId,
                commentCreate
        ));
    }

    @DeleteMapping("/comments/{commentId}")
    public BaseResponse<Void> deleteComment(
            @PathVariable Long commentId
    ){
        commentService.deleteFreeComment(
                authService.getLoginMemberId(),
                commentId
        );
        return BaseResponse.ok();
    }
}
