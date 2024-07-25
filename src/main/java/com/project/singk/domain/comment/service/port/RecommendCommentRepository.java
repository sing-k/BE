package com.project.singk.domain.comment.service.port;

import com.project.singk.domain.comment.domain.RecommendComment;

public interface RecommendCommentRepository {
    RecommendComment save(RecommendComment comment);
    RecommendComment findById(Long id);
    void delete(Long id);
}
