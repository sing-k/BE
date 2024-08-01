package com.project.singk.domain.like.controller.port;

import com.project.singk.global.domain.PkResponseDto;

public interface FreeLikeService {
    PkResponseDto createPostLike(Long memberId, Long postId);
    void deletePostLike(Long memberId, Long postId);
    PkResponseDto createCommentLike(Long memberId, Long commentId);
    void deleteCommentLike(Long memberId, Long commentId);
}
