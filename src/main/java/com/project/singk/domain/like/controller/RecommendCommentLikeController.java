package com.project.singk.domain.like.controller;

import com.project.singk.domain.like.controller.port.RecommendCommentLikeService;
import com.project.singk.domain.member.controller.port.AuthService;
import com.project.singk.global.api.BaseResponse;
import com.project.singk.global.domain.PkResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/recommend/comments")
public class RecommendCommentLikeController {

    private final RecommendCommentLikeService likeService;
    private final AuthService authService;

    @PostMapping("/{commentId}/likes")
    public BaseResponse<PkResponseDto> addLike(@PathVariable Long postId){

        return BaseResponse.created(likeService.addLike(
                authService.getLoginMemberId(),
                postId));

    }
    @DeleteMapping("/likes/{likeId}")
    public BaseResponse<Void> deleteLike(@PathVariable Long likeId){
        likeService.delete(likeId);
        return BaseResponse.ok();
    }
}
