package com.project.singk.domain.comment.controller.port;

import com.project.singk.domain.comment.controller.response.CommentResponse;
import com.project.singk.domain.comment.domain.CommentCreate;
import com.project.singk.global.domain.PkResponseDto;

import java.util.List;

public interface FreeCommentService {

    List<CommentResponse> getFreeComments(Long memberId, Long postId);
    List<CommentResponse> getMyFreeComments(Long memberId);
    PkResponseDto createFreeComment(Long memberId, Long postId, Long parentId, CommentCreate commentCreate);
    PkResponseDto updateFreeComment(Long memberId, Long commentId, CommentCreate commentCreate);
    void deleteFreeComment(Long memberId, Long postId, Long commentId);
}
