package com.project.singk.domain.post.controller.port;

import com.project.singk.domain.post.controller.response.RecommendPostListResponse;
import com.project.singk.domain.post.controller.response.RecommendPostResponse;
import com.project.singk.domain.post.domain.RecommendPostCreate;
import com.project.singk.domain.post.domain.RecommendPostUpdate;
import com.project.singk.global.api.PageResponse;
import com.project.singk.global.domain.PkResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RecommendPostService {
    PkResponseDto createRecommendPost(Long memberId, RecommendPostCreate post, MultipartFile image);
    RecommendPostResponse getRecommendPost(Long postId);
    PageResponse<RecommendPostListResponse> getRecommendPosts(int offset, int limit, String sort, String filter, String keyword);
    PageResponse<RecommendPostListResponse> getMyRecommendPosts(Long memberId, int offset, int limit, String sort, String filter, String keyword);
    PkResponseDto updateRecommendPost(Long memberId, Long postId, RecommendPostUpdate req);
    void deleteRecommendPost(Long memberId, Long postId);
}
