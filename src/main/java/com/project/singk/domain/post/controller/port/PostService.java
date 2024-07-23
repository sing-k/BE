package com.project.singk.domain.post.controller.port;

import com.project.singk.domain.post.domain.PostCreate;
import com.project.singk.global.domain.PkResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {
    PkResponseDto create(Long memberId, PostCreate post, MultipartFile thumbnail);
}
