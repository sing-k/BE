package com.project.singk.domain.like.controller;

import com.project.singk.domain.like.controller.port.FreeLikeService;
import com.project.singk.domain.member.controller.port.AuthService;
import com.project.singk.global.api.BaseResponse;
import com.project.singk.global.domain.PkResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes/posts/free")
public class FreeLikeController {

    private final FreeLikeService likeService;
    private final AuthService authService;

    @PostMapping("/{postId}")
    public BaseResponse<PkResponseDto> addPostLike(@PathVariable Long postId){

        return BaseResponse.created(likeService.createPostLike(
                authService.getLoginMemberId(),
                postId));

    }
    @DeleteMapping("/{postId}")
    public BaseResponse<Void> deletePostLike(@PathVariable Long postId){
        likeService.deletePostLike(
                authService.getLoginMemberId(),
                postId
        );
        return BaseResponse.ok();
    }

    @PostMapping("/comments/{commentId}")
    public BaseResponse<PkResponseDto> createCommentLike(@PathVariable Long commentId){
        return BaseResponse.created(likeService.createCommentLike(
                authService.getLoginMemberId(),
                commentId
        ));
    }
    @DeleteMapping("/comments/{commentId}")
    public BaseResponse<Void> deleteCommentLike(@PathVariable Long commentId){
        likeService.deleteCommentLike(
                authService.getLoginMemberId(),
                commentId
        );
        return BaseResponse.ok();
    }
}
