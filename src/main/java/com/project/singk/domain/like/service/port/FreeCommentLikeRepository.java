package com.project.singk.domain.like.service.port;

import com.project.singk.domain.like.domain.FreeCommentLike;

import java.util.Optional;

public interface FreeCommentLikeRepository {
    FreeCommentLike save(FreeCommentLike like);
    Optional<FreeCommentLike> findByMemberIdAndCommentId(Long memberId, Long commentId);
    boolean existsByMemberIdAndCommentId(Long memberId, Long commentId);
    void deleteById(Long commentId);
}
