package com.project.singk.domain.like.service.port;

import com.project.singk.domain.like.domain.RecommendCommentLike;

import java.util.Optional;

public interface RecommendCommentLikeRepository {
    RecommendCommentLike save(RecommendCommentLike like);
    Optional<RecommendCommentLike> findByMemberIdAndCommentId(Long memberId, Long commentId);
    boolean existsByMemberIdAndCommentId(Long memberId, Long commentId);
    void deleteById(Long likeId);
}
