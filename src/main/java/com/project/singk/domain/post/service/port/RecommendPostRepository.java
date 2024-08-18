package com.project.singk.domain.post.service.port;

import com.project.singk.domain.post.domain.RecommendPost;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface RecommendPostRepository {

    RecommendPost save(RecommendPost post);
    List<RecommendPost> saveAll(List<RecommendPost> posts);
    RecommendPost getById(Long postId);
    Optional<RecommendPost> findById(Long postId);
    Page<RecommendPost> findAll(int offset, int limit, String sort, String filter, String keyword);
    Page<RecommendPost> findAllByMemberId(Long memberId, int offset, int limit);
    void deleteById(Long postId);
}
