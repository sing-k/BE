package com.project.singk.domain.post.controller;

import com.project.singk.domain.member.controller.port.AuthService;
import com.project.singk.domain.post.controller.port.RecommendPostService;
import com.project.singk.domain.post.domain.RecommendPostCreate;
import com.project.singk.global.api.BaseResponse;
import com.project.singk.global.domain.PkResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/recommend")
public class RecommendPostController {

    private final RecommendPostService recommendPostService;
    private final AuthService authService;

    @PostMapping("")
    public BaseResponse<PkResponseDto> createPost(
            @RequestPart("post") RecommendPostCreate post,
            @RequestPart("image") MultipartFile image
    ) {
        return BaseResponse.created(recommendPostService.createRecommendPost(
                authService.getLoginMemberId(),
                post,
                image)
        );
    }

}
