package com.project.singk.domain.comment.service.port;

import com.project.singk.domain.comment.domain.CommentSimplified;
import com.project.singk.domain.comment.domain.FreeComment;

import java.util.List;
import java.util.Optional;

public interface FreeCommentRepository {
    FreeComment save(FreeComment comment);
    Optional<FreeComment> findById(Long commentId);
    FreeComment getById(Long commentId);
    void deleteById(Long commentId);
    List<CommentSimplified> findAllByPostId(Long postId);
    List<CommentSimplified> findAllByMemberIdAndPostId(Long memberId, Long postId);
}
