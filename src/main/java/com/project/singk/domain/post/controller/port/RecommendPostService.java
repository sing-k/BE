package com.project.singk.domain.post.controller.port;

import com.project.singk.domain.post.domain.RecommendPostCreate;
import com.project.singk.global.domain.PkResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface RecommendPostService {
    PkResponseDto createRecommendPost(Long memberId, RecommendPostCreate post, MultipartFile image);
}
