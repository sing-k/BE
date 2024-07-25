package com.project.singk.domain.post.controller.port;

import com.project.singk.domain.post.controller.response.RecommendPostResponse;
import com.project.singk.domain.post.domain.RecommendPostCreate;
import com.project.singk.domain.post.domain.RecommendPostUpdate;
import com.project.singk.global.domain.PkResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RecommendPostService {
    PkResponseDto createRecommendPost(Long memberId, RecommendPostCreate post, MultipartFile image);
    RecommendPostResponse findById(Long id);
    List<RecommendPostResponse> findAll();
    PkResponseDto updateById(Long id, RecommendPostUpdate req);
    void deleteById(Long id);
}
