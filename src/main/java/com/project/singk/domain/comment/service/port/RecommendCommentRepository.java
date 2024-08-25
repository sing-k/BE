package com.project.singk.domain.comment.service.port;

import com.project.singk.domain.comment.domain.RecommendComment;
import com.project.singk.domain.comment.domain.CommentSimplified;

import java.util.List;
import java.util.Optional;

public interface RecommendCommentRepository {
    RecommendComment save(RecommendComment comment);
    RecommendComment getById(Long commentId);
    Optional<RecommendComment> findById(Long commentId);
    void deleteById(Long commentId);
    List<CommentSimplified> findAllByPostId(Long postId);
    List<CommentSimplified> findAllByMemberIdAndPostId(Long memberId, Long postId);
}
