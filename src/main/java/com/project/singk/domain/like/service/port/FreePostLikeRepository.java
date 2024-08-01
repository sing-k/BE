package com.project.singk.domain.like.service.port;

import com.project.singk.domain.like.domain.FreePostLike;

import java.util.Optional;

public interface FreePostLikeRepository {
    FreePostLike save(FreePostLike like);
    Optional<FreePostLike> findByMemberIdAndPostId(Long memberId, Long postId);
    boolean existsByMemberIdAndPostId(Long memberId, Long postId);
    void deleteById(Long likeId);
}
