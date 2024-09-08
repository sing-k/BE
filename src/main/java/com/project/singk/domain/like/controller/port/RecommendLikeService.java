package com.project.singk.domain.like.controller.port;

import com.project.singk.global.domain.PkResponseDto;

public interface RecommendLikeService {
    boolean getPostLike(Long memberId, Long postId);
    boolean getCommentLike(Long memberId, Long commentId);
    PkResponseDto createPostLike(Long memberId, Long postId);
    void deletePostLike(Long memberId, Long postId);
    PkResponseDto createCommentLike(Long memberId, Long commentId);
    void deleteCommentLike(Long memberId, Long commentId);
}
