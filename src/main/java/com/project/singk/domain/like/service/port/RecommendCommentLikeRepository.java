package com.project.singk.domain.like.service.port;

import com.project.singk.domain.like.domain.RecommendCommentLike;

public interface RecommendCommentLikeRepository {
    RecommendCommentLike save(RecommendCommentLike like);
    void delete(Long id);
}
