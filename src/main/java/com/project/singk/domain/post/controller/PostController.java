package com.project.singk.domain.post.controller;

import com.project.singk.domain.member.controller.port.AuthService;
import com.project.singk.domain.post.controller.port.PostService;
import com.project.singk.domain.post.domain.PostCreate;
import com.project.singk.global.api.BaseResponse;
import com.project.singk.global.domain.PkResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
                request,
                thumbnailImage));
    }
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
//        postService.deletePost(id);
//        return ResponseEntity.noContent().build();
//    }
}
