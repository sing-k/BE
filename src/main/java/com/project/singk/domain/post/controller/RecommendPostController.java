package com.project.singk.domain.post.controller;

import com.project.singk.domain.member.controller.port.AuthService;
import com.project.singk.domain.post.controller.request.FilterSort;
import com.project.singk.domain.post.controller.request.PostSort;
import com.project.singk.domain.post.controller.response.RecommendPostListResponse;
import com.project.singk.domain.post.controller.response.RecommendPostResponse;
import com.project.singk.domain.post.domain.RecommendPostCreate;
import com.project.singk.domain.post.controller.port.RecommendPostService;
import com.project.singk.domain.post.domain.RecommendPostUpdate;
import com.project.singk.global.api.BaseResponse;
import com.project.singk.global.api.OffsetPageResponse;
import com.project.singk.global.domain.PkResponseDto;
import com.project.singk.global.validate.ValidEnum;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        return BaseResponse.created(recommendPostService.createRecommendPost(
                authService.getLoginMemberId(),
                post,
                image
        ));
    }
    @GetMapping("/{postId}")
    public BaseResponse<RecommendPostResponse> getRecommendPost(@PathVariable Long postId){
        return BaseResponse.ok(recommendPostService.getRecommendPost(
                authService.getLoginMemberId(),
                postId
        ));
    }

    @GetMapping("")
    public BaseResponse<OffsetPageResponse<RecommendPostListResponse>> getRecommendPosts(
            @Range(min = 0, max = 1000, message = "offset은 0에서 1000사이의 값 이어야 합니다.") @RequestParam("offset") int offset,
            @Range(min = 0, max = 50, message = "limit은 0에서 50사이의 값 이어야 합니다.") @RequestParam("limit") int limit,
            @RequestParam(name = "sort") @ValidEnum(enumClass = PostSort.class) String sort,
            @RequestParam(name = "filter", required = false) @ValidEnum(enumClass = FilterSort.class, required = false) String filter,
            @RequestParam(name = "keyword", required = false) String keyword
    ) {

        return BaseResponse.ok(recommendPostService.getRecommendPosts(
                authService.getLoginMemberId(),
                offset,
                limit,
                sort,
                filter,
                keyword
        ));
    }

    @GetMapping("/me")
    public BaseResponse<OffsetPageResponse<RecommendPostListResponse>> getMyRecommendPosts(
            @Range(min = 0, max = 1000, message = "offset은 0에서 1000사이의 값 이어야 합니다.") @RequestParam("offset") int offset,
            @Range(min = 0, max = 50, message = "limit은 0에서 50사이의 값 이어야 합니다.") @RequestParam("limit") int limit,
            @RequestParam(name = "sort") @ValidEnum(enumClass = PostSort.class) String sort,
            @RequestParam(name = "filter", required = false) @ValidEnum(enumClass = FilterSort.class, required = false) String filter,
            @RequestParam(name = "keyword", required = false) String keyword
    ) {

        return BaseResponse.ok(recommendPostService.getMyRecommendPosts(
                authService.getLoginMemberId(),
                offset,
                limit,
                sort,
                filter,
                keyword
        ));
    }

    @PutMapping("/{postId}")
    public BaseResponse<PkResponseDto> updateRecommendPost(
            @PathVariable Long postId,
            @RequestBody RecommendPostUpdate post
    ) {
        return BaseResponse.ok(recommendPostService.updateRecommendPost(
                authService.getLoginMemberId(),
                postId,
                post
        ));
    }

    @DeleteMapping("/{postId}")
    public BaseResponse<Void> deleteRecommendPost(
            @PathVariable Long postId
    ) {
        recommendPostService.deleteRecommendPost(
                authService.getLoginMemberId(),
                postId
        );
        return BaseResponse.ok();
    }

}
