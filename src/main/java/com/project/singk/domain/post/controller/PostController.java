package com.project.singk.domain.post.controller;

import com.project.singk.domain.member.controller.port.AuthService;
import com.project.singk.domain.post.controller.port.PostService;
import com.project.singk.domain.post.controller.response.PostResponse;
import com.project.singk.domain.post.controller.response.RecommendPostResponse;
import com.project.singk.domain.post.domain.PostCreate;
import com.project.singk.domain.post.domain.RecommendPostUpdate;
import com.project.singk.global.api.BaseResponse;
import com.project.singk.global.domain.PkResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final AuthService authService;

    @PostMapping("/create")
    public BaseResponse<PkResponseDto> createPost(
            @RequestPart("post") PostCreate request,
            @RequestPart("thumbnailImage") MultipartFile thumbnailImage) {
        return BaseResponse.created(postService.create(
                authService.getLoginMemberId(),
                request));
    }
    //Todo : auth 관련 처리 하기
    @GetMapping("/{id}")
    public BaseResponse<PostResponse> getPost(@PathVariable Long id){
        PostResponse dto = postService.findById(id);
        return BaseResponse.ok(dto);
    }

    //Todo : page처리 + 검색 + 필터링
    @GetMapping("")
    public BaseResponse<List<PostResponse>> getPostList(){
        List<PostResponse> dtoList = postService.findAll();
        return BaseResponse.ok(dtoList);
    }

    @PutMapping("/{id}")
    public BaseResponse<PkResponseDto> updatePost(@PathVariable Long id,
                                                  @RequestPart(value = "post",required = false) PostCreate post){
        PkResponseDto dto = postService.updateById(id,post);
        return BaseResponse.ok(dto);
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> deletePost(@PathVariable Long id){
        postService.deleteById(id);
        return BaseResponse.ok();
    }
}
