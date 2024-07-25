package com.project.singk.domain.comment.controller;

import com.project.singk.domain.comment.controller.port.RecommendCommentService;
import com.project.singk.domain.comment.domain.RecommendCommentCreate;
import com.project.singk.domain.member.controller.port.AuthService;
import com.project.singk.global.api.BaseResponse;
import com.project.singk.global.domain.PkResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/recommend/")
public class RecommendCommentController {

    private final RecommendCommentService commentService;
    private final AuthService authService;

    @PostMapping("/{postId}/comments/{parentId}")
    public BaseResponse<PkResponseDto> createComment(
            @RequestBody RecommendCommentCreate req,
            @PathVariable Long postId,
            @PathVariable Long parentId){
        return BaseResponse.created(commentService.createComment(
                authService.getLoginMemberId(),postId,parentId,req)
        );
    }
    //Todo : auth 관련 처리 하기
    @PutMapping("/comments/{id}")
    public BaseResponse<PkResponseDto> updateComment(
            @PathVariable Long commentId
            ,@RequestBody RecommendCommentCreate req){
        return BaseResponse.ok(commentService.updateComment(commentId,req));
    }

    @DeleteMapping("/comments/{id}")
    public BaseResponse<Void> deleteComment(@PathVariable Long commentId){
        commentService.deleteComment(commentId);
        return BaseResponse.ok();
    }
    //Todo: 댓글 조회 만들기
}
