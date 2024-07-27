package com.project.singk.domain.post.service.port;

import com.project.singk.domain.post.domain.Post;
import com.project.singk.domain.post.domain.RecommendPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RecommendPostRepository {

    RecommendPost save(RecommendPost post);
    RecommendPost getById(Long postId);
    Optional<RecommendPost> findById(Long postId);
    Page<RecommendPost> findAll(int offset, int limit, String sort, String filter, String keyword);
    Page<RecommendPost> findAllByMemberId(Long memberId, int offset, int limit, String sort, String filter, String keyword);
    void deleteById(Long postId);
}
