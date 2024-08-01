package com.project.singk.domain.like.service.port;

import com.project.singk.domain.like.domain.RecommendPostLike;

import java.util.Optional;

public interface RecommendPostLikeRepository {
    RecommendPostLike save(RecommendPostLike like);
    Optional<RecommendPostLike> findByMemberIdAndPostId(Long memberId, Long postId);
    boolean existsByMemberIdAndPostId(Long memberId, Long postId);
    void deleteById(Long likeId);
}
