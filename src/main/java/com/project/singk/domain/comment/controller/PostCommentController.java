package com.project.singk.domain.comment.controller;

import com.project.singk.domain.comment.controller.port.PostCommentService;
import com.project.singk.domain.comment.domain.PostCommentCreate;
import com.project.singk.domain.member.controller.port.AuthService;
import com.project.singk.global.api.BaseResponse;
import com.project.singk.global.domain.PkResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostCommentController {

    private final PostCommentService commentService;
    private final AuthService authService;

    // Todo : 부모 댓글 null 일 시 처리
    @PostMapping("/{postId}/comments/{parentId}")
    public BaseResponse<PkResponseDto> createComment(
            @RequestBody PostCommentCreate req,
            @PathVariable Long postId,
            @PathVariable(required = false) Long parentId){
        return BaseResponse.created(commentService.createComment(
                authService.getLoginMemberId(),postId,parentId,req)
        );
    }
    //Todo : auth 관련 처리 하기
    @PutMapping("/comments/{id}")
    public BaseResponse<PkResponseDto> updateComment(
            @PathVariable Long commentId
            ,@RequestBody PostCommentCreate req){
        return BaseResponse.ok(commentService.updateComment(commentId,req));
    }

    @DeleteMapping("/comments/{id}")
    public BaseResponse<Void> deleteComment(@PathVariable Long commentId){
        commentService.deleteComment(commentId);
        return BaseResponse.ok();
    }
    //Todo: 댓글 조회 만들기
}
