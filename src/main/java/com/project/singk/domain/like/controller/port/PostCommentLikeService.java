package com.project.singk.domain.like.controller.port;

import com.project.singk.domain.like.domain.PostCommentLike;
import com.project.singk.global.domain.PkResponseDto;

public interface PostCommentLikeService {
    PkResponseDto addLike(Long memberId, Long postId);
    void delete(Long id);
}
