package com.project.singk.domain.like.service.port;

import com.project.singk.domain.like.domain.RecommendLike;

public interface RecommendLikeRepository {
    RecommendLike save(RecommendLike like);
    void delete(Long id);
}
