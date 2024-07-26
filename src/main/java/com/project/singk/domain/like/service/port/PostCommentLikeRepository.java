package com.project.singk.domain.like.service.port;

import com.project.singk.domain.like.domain.PostCommentLike;

public interface PostCommentLikeRepository {
    PostCommentLike save(PostCommentLike like);
    void delete(Long id);
}
