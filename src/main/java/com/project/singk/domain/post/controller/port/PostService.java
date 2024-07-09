package com.project.singk.domain.post.controller.port;

import com.project.singk.domain.post.controller.request.PostCreateRequest;
import com.project.singk.domain.post.domain.Post;
import com.project.singk.global.domain.PkResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {
    PkResponseDto create(Long memberId, PostCreateRequest post, MultipartFile thumbnail);
}
