package com.project.singk.domain.post.controller;

import com.project.singk.domain.member.controller.port.AuthService;
import com.project.singk.domain.post.controller.port.FreePostService;
import com.project.singk.domain.post.controller.request.FilterSort;
import com.project.singk.domain.post.controller.request.PostSort;
import com.project.singk.domain.post.controller.response.FreePostListResponse;
import com.project.singk.domain.post.controller.response.FreePostResponse;
import com.project.singk.domain.post.domain.FreePostCreate;
import com.project.singk.global.api.BaseResponse;
import com.project.singk.global.api.PageResponse;
import com.project.singk.global.domain.PkResponseDto;
import com.project.singk.global.validate.ValidEnum;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/free")
public class FreePostController {

    private final FreePostService freePostService;
    private final AuthService authService;

    @PostMapping("")
    public BaseResponse<PkResponseDto> createFreePost(
            @RequestBody FreePostCreate freePostCreate
    ) {
        return BaseResponse.created(freePostService.createFreePost(
                authService.getLoginMemberId(),
                freePostCreate
        ));
    }

    @GetMapping("/{postId}")
    public BaseResponse<FreePostResponse> getFreePost(@PathVariable Long postId){
        return BaseResponse.ok(freePostService.getFreePost(
                authService.getLoginMemberId(),
                postId
        ));
    }

    @GetMapping("")
    public BaseResponse<PageResponse<FreePostListResponse>> getFreePosts(
            @Range(min = 0, max = 1000, message = "offset은 0에서 1000사이의 값 이어야 합니다.") @RequestParam("offset") int offset,
            @Range(min = 0, max = 50, message = "limit은 0에서 50사이의 값 이어야 합니다.") @RequestParam("limit") int limit,
            @RequestParam(name = "sort") @ValidEnum(enumClass = PostSort.class) String sort,
            @RequestParam(name = "filter", required = false) @ValidEnum(enumClass = FilterSort.class, required = false) String filter,
            @RequestParam(name = "keyword", required = false) String keyword
    ) {

        return BaseResponse.ok(freePostService.getFreePosts(
                authService.getLoginMemberId(),
                offset,
                limit,
                sort,
                filter,
                keyword
        ));
    }

    @GetMapping("/me")
    public BaseResponse<PageResponse<FreePostListResponse>> getMyFreePosts(
            @Range(min = 0, max = 1000, message = "offset은 0에서 1000사이의 값 이어야 합니다.") @RequestParam("offset") int offset,
            @Range(min = 0, max = 50, message = "limit은 0에서 50사이의 값 이어야 합니다.") @RequestParam("limit") int limit,
            @RequestParam(name = "sort") @ValidEnum(enumClass = PostSort.class) String sort,
            @RequestParam(name = "filter", required = false) @ValidEnum(enumClass = FilterSort.class, required = false) String filter,
            @RequestParam(name = "keyword", required = false) String keyword
    ) {

        return BaseResponse.ok(freePostService.getMyFreePosts(
                authService.getLoginMemberId(),
                offset,
                limit,
                sort,
                filter,
                keyword
        ));
    }

    @PutMapping("/{postId}")
    public BaseResponse<PkResponseDto> updateFreePost(
            @PathVariable Long postId,
            @RequestBody FreePostCreate freePostCreate){

        return BaseResponse.ok(freePostService.updateFreePost(
                authService.getLoginMemberId(),
                postId,
                freePostCreate
        ));
    }

    @DeleteMapping("/{postId}")
    public BaseResponse<Void> deleteFreePost(@PathVariable Long postId){
        freePostService.deleteFreePost(
                authService.getLoginMemberId(),
                postId
        );
        return BaseResponse.ok();
    }
}
