package com.project.singk.domain.post.controller;

import com.project.singk.domain.member.controller.port.AuthService;
import com.project.singk.domain.post.controller.response.RecommendPostResponse;
import com.project.singk.domain.post.domain.RecommendPostCreate;
import com.project.singk.domain.post.controller.port.RecommendPostService;
import com.project.singk.domain.post.domain.RecommendPostUpdate;
import com.project.singk.global.api.BaseResponse;
import com.project.singk.global.domain.PkResponseDto;
import lombok.RequiredArgsConstructor;
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
            @RequestPart("image") MultipartFile image
    ) {
        return BaseResponse.created(recommendPostService.createRecommendPost(
                authService.getLoginMemberId(),
                post,
                image)
        );
    }
    //Todo : auth 관련 처리 하기
    @GetMapping("/{id}")
    public BaseResponse<RecommendPostResponse> getPost(@PathVariable Long id){
        RecommendPostResponse dto = recommendPostService.findById(id);
        return BaseResponse.ok(dto);
    }

    //Todo : page처리 + 검색 + 필터링
    @GetMapping("")
    public BaseResponse<List<RecommendPostResponse>> getPostList(){
        List<RecommendPostResponse> dtoList = recommendPostService.findAll();
        return BaseResponse.ok(dtoList);
    }

    @PutMapping("/{id}")
    public BaseResponse<PkResponseDto> updatePost(@PathVariable Long id,
                                                          @RequestPart(value = "post",required = false) RecommendPostUpdate post,
                                                          @RequestPart(value = "image",required = false)MultipartFile image){
        PkResponseDto dto = recommendPostService.updateById(id,post);
        return BaseResponse.ok(dto);
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> deletePost(@PathVariable Long id){
        recommendPostService.deleteById(id);
        return BaseResponse.ok();
    }

}
