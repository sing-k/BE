package com.project.singk.domain.comment.controller.port;

import com.project.singk.domain.comment.domain.PostCommentCreate;
import com.project.singk.domain.comment.domain.RecommendCommentCreate;
import com.project.singk.global.domain.PkResponseDto;

public interface PostCommentService {
    PkResponseDto createComment(Long memberId, Long postId, Long parentId, PostCommentCreate req);
    PkResponseDto updateComment(Long commentId, PostCommentCreate req);
    void deleteComment(Long id);
}
