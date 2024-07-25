package com.project.singk.domain.comment.controller.port;

import com.project.singk.domain.comment.domain.RecommendCommentCreate;
import com.project.singk.global.domain.PkResponseDto;

public interface RecommendCommentService {
    PkResponseDto createComment(Long memberId,Long postId, Long parentId, RecommendCommentCreate req);
    PkResponseDto updateComment(Long commentId, RecommendCommentCreate req);
    void deleteComment(Long id);
}
