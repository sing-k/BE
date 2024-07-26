package com.project.singk.domain.like.controller;

import com.project.singk.domain.like.controller.port.PostCommentLikeService;
import com.project.singk.domain.member.controller.port.AuthService;
import com.project.singk.global.api.BaseResponse;
import com.project.singk.global.domain.PkResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/comments")
public class PostCommentLikeController {

    private final PostCommentLikeService likeService;
    private final AuthService authService;

    @PostMapping("/{commentId}/likes")
    public BaseResponse<PkResponseDto> addLike(@PathVariable Long commentId){

        return BaseResponse.created(likeService.addLike(
                authService.getLoginMemberId(),
                commentId));

    }
    @DeleteMapping("/likes/{likeId}")
    public BaseResponse<Void> deleteLike(@PathVariable Long likeId){
        likeService.delete(likeId);
        return BaseResponse.ok();
    }
}
