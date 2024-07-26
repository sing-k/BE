package com.project.singk.domain.comment.service.port;

import com.project.singk.domain.comment.domain.PostComment;
import com.project.singk.domain.comment.domain.RecommendComment;

public interface PostCommentRepository {
    PostComment save(PostComment comment);
    PostComment findById(Long id);
    void delete(Long id);
}
