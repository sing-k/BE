package com.project.singk.domain.comment.controller.port;

import com.project.singk.domain.comment.controller.response.CommentResponse;
import com.project.singk.domain.comment.domain.CommentCreate;
import com.project.singk.global.domain.PkResponseDto;

import java.util.List;

public interface RecommendCommentService {
    List<CommentResponse> getRecommendComments(Long memberId, Long postId);
    PkResponseDto createRecommendComment(Long memberId, Long postId, Long parentId, CommentCreate commentCreate);
    PkResponseDto updateRecommendComment(Long memberId, Long commentId, CommentCreate commentCreate);
    void deleteRecommendComment(Long memberId, Long commentId);
}
