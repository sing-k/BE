package com.project.singk.domain.like.service.port;

import com.project.singk.domain.like.domain.PostLike;

public interface PostLikeRepository {
    PostLike save(PostLike like);
    void delete(Long id);
}
